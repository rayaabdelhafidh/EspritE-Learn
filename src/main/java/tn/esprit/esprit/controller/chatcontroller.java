package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;

public class chatcontroller {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> listView;

    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    @FXML
    public void sendChatGPTRequest() {
        String prompt = searchField.getText();

        if (!prompt.isEmpty()) {
            try {
                String response = sendChatCompletionRequest(prompt);
                // Extraire le contenu de la réponse JSON
                String content = extractContentFromResponse(response);
                // Ajouter le contenu à la ListView
                listView.getItems().add(content);
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer les erreurs d'envoi de requête ici
            }
        } else {
            System.out.println("Veuillez entrer un prompt avant d'envoyer la requête à l'API ChatGPT.");
        }
    }

    private String sendChatCompletionRequest(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = """
                {
                    "model": "gpt-3.5-turbo-0125",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(prompt);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url(OPENAI_ENDPOINT)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    private String extractContentFromResponse(String jsonResponse) {
        // Parse the JSON response and extract the content field
        // Vous pouvez utiliser une bibliothèque de parsing JSON comme Gson ou Jackson pour faciliter l'extraction
        // Pour l'exemple, nous allons simplement extraire le contenu avec des opérations de chaîne
        int startIndex = jsonResponse.indexOf("\"content\":") + "\"content\":".length() + 1;
        int endIndex = jsonResponse.indexOf("\"", startIndex + 1);

        return jsonResponse.substring(startIndex, endIndex);

    }

}
