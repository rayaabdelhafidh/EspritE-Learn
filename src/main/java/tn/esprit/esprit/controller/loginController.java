package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;
import javafx.scene.web.WebView;
//import org.json.simple.JSONObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class loginController implements Initializable {
    @FXML
    private TextField emailf;

    @FXML
    private PasswordField passf;
    @FXML
    private Button reset;
    @FXML
    private TextField captchaTextField;

    @FXML
    private WebView webView;
    private final ServiceUser su = new ServiceUser();

    public int idSession;

    @FXML
    public void login(ActionEvent event) throws IOException, SQLException {
        /*String captchaResponse = (String) webView.getEngine().executeScript("document.getElementById('g-recaptcha-response').value");
        boolean isCaptchaValid = verifyCaptcha(captchaResponse);
        if (!isCaptchaValid) {
            new Alert(Alert.AlertType.ERROR, "Please complete the reCAPTCHA challenge", ButtonType.CLOSE).show();
            return;
        }*/
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Récupération de l'email et du mot de passe saisis par l'utilisateur
        String email = emailf.getText();
        String password = passf.getText();

        // Validation des champs email et mot de passe
        if (!validateFields(email, password)) {
            new Alert(Alert.AlertType.ERROR, "Tous les champs sont obligatoires", ButtonType.CLOSE).show();
            return;
        }

        // Validation de la syntaxe de l'email
        if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Veuillez entrer un email valide", ButtonType.CLOSE).show();
            return;
        }

        // Authentification de l'utilisateur et récupération du rôle
        String loginResult = su.login(email, password);
        if (loginResult.equals("Admin")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/DashbordAdmin.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else if (loginResult.equals("Etudiant")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/ProfileEtudiant.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else if (loginResult.equals("Enseignant")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/ProfileEnseignant.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else {
            new Alert(Alert.AlertType.ERROR, loginResult, ButtonType.CLOSE).show();
        }
        // Affichage de l'alerte de connexion réussie après les opérations d'authentification
        new Alert(Alert.AlertType.INFORMATION, "Connexion réussie!", ButtonType.OK).show();
    }
    private boolean verifyCaptcha(String captchaResponse) {
        // Google's reCAPTCHA verification API endpoint
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String secretKey = "6LdgqIgpAAAAAIL7zt1gZh87stU7vGsgR3Yl7h7X"; // Your reCAPTCHA secret key

        try {
            // Create a URL object with the verification API endpoint
            URL obj = new URL(url);

            // Create a HttpURLConnection object to send the POST request
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to POST
            con.setRequestMethod("POST");

            // Add request headers
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Construct the POST data
            String postData = "secret=" + URLEncoder.encode(secretKey, "UTF-8") +
                    "&response=" + URLEncoder.encode(captchaResponse, "UTF-8");

            // Enable the connection to output data
            con.setDoOutput(true);

            // Write the POST data to the connection
            con.getOutputStream().write(postData.getBytes("UTF-8"));

            // Get the response code from the server
            int responseCode = con.getResponseCode();

            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            // Read response line by line
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Check if the captcha verification was successful
            return jsonResponse.getBoolean("success");

        } catch (IOException e) {
            e.printStackTrace();
            return false; // Verification failed due to an exception
        }
    }

    // Méthode pour valider les champs email et mot de passe
    private boolean validateFields(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    // Méthode pour valider la syntaxe de l'email
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    // Méthode pour naviguer vers une nouvelle scène
    // Méthode pour naviguer vers une nouvelle scène
    private void navigateToScene(ActionEvent event, String sceneName) {
        try {
            // Chargement de la nouvelle scène à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            Parent root = loader.load();

            // Création de la scène
            Scene scene = new Scene(root);

            // Récupération de la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Gestion de l'erreur de chargement du fichier FXML
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement de la scène", ButtonType.CLOSE).show();
        } catch (NullPointerException e) {
            // Gestion de l'erreur de référence nulle
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fichier FXML introuvable", ButtonType.CLOSE).show();
        }
    }
    @FXML
    private void GoToregister(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent root = loader.load();
            emailf.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void reset(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("/ResetPwd.fxml"));

        Scene scene2 = new Scene(page2);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene2);
        app_stage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com/recaptcha/api/fallback?k=6LdgqIgpAAAAAD7HQvSgIRZJwhqwUH-MkbgBtMww");
    }
}
