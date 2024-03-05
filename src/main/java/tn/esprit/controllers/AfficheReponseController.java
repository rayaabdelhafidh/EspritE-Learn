package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.Models.Option;
import tn.esprit.Models.QRCodeGenerator;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceOption;
import tn.esprit.Services.ServiceQuestion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AfficheReponseController implements Initializable {
    @FXML
    private ListView<Question> ListviewQuestion;

    private ServiceQuestion serviceQuestion;

    ServiceOption serviceOption = new ServiceOption();
    private Map<Integer, Boolean> selectedOptions = new HashMap<>();

    private int quiz_id;

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setServiceOption(ServiceOption serviceOption) {
        this.serviceOption = serviceOption;
    }

    public void setServiceQuestion(ServiceQuestion serviceQuestion) {
        this.serviceQuestion = serviceQuestion;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void Affiche(ActionEvent event) {
        List<Question> questions = serviceQuestion.getQuestionsByQuizId(quiz_id);
        ListviewQuestion.getItems().addAll(questions);

        ListviewQuestion.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Question> call(ListView<Question> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Question question, boolean empty) {
                        super.updateItem(question, empty);
                        if (empty || question == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            List<Option> options = serviceOption.getOptionByQuestion(question.getQuestionId());

                            VBox container = new VBox(); // Conteneur vertical pour les éléments
                            Label questionLabel = new Label("Question: " + question.getContent() + "?");
                            container.getChildren().add(questionLabel);

                            // Ajouter une CheckBox pour chaque option
                            for (Option option : options) {
                                CheckBox optionCheckBox = new CheckBox(option.getOption_content());

                                // Ajouter un gestionnaire d'événements pour la sélection/dé-sélection de la CheckBox
                                optionCheckBox.setOnAction(event -> {
                                    if (optionCheckBox.isSelected()) {
                                        // Action à effectuer lorsque la CheckBox est sélectionnée
                                        selectedOptions.put(option.getOption_id(), true);
                                        System.out.println("Option sélectionnée avec ID : " + option.getOption_id());
                                    } else {
                                        // Action à effectuer lorsque la CheckBox est désélectionnée
                                        selectedOptions.put(option.getOption_id(), false);
                                        System.out.println("Option désélectionnée avec ID : " + option.getOption_id());
                                    }
                                });

                                // Ajoutez la CheckBox au conteneur
                                container.getChildren().add(optionCheckBox);
                            }

                            // Définir le conteneur comme graphique de la cellule
                            setGraphic(container);
                        }
                    }


                };
            }
        });
    }

    private String formatOptions(List<Option> options) {
        StringBuilder optionsString = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            optionsString.append(options.get(i).getOption_content() + "\n");
        }
        return optionsString.toString();
    }

    @FXML
    void lireSelection(ActionEvent event) {
        int totalScore = 0;

        // Parcourir les options sélectionnées
        for (Map.Entry<Integer, Boolean> entry : selectedOptions.entrySet()) {
            int optionId = entry.getKey();
            System.out.println("select"+optionId);
            boolean isSelected = entry.getValue();
            System.out.println("select"+isSelected);

            // Vous devez récupérer la réponse attendue de la base de données ou d'une autre source
            boolean isCorrect = serviceOption.isOptionCorrect(optionId);
            System.out.println("sel"+isCorrect);

            if (isSelected && isCorrect) {
                int questionId = serviceOption.getQuestionIdByOptionId(optionId);
                System.out.println("select"+questionId);
                // Si l'option est sélectionnée et qu'elle est correcte, augmentez le score
                int questionScore = serviceQuestion.getQuestionScore(questionId);
                System.out.println("sel"+questionScore);
                totalScore =  totalScore+ questionScore;
                // Score pour chaque option correcte
            }
            System.out.println("Score total : " + totalScore);
        }
        afficherScoreTotal(totalScore);


        // Afficher le score total

        // Vous pouvez également afficher le score sur l'interface utilisateur ou faire d'autres traitements nécessaires.
    }
    private void afficherScoreTotal(int totalScore ) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resultat.fxml"));
        Parent root = loader.load();
        ResultatController controller=loader.getController();
            controller.setTotalScore(totalScore);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Resultat");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
@FXML
    void generateQRCode(ActionEvent event) {
        try {
            // Générer le texte du quiz à partir des questions
            StringBuilder quizTextBuilder = new StringBuilder();
            for (Question question : ListviewQuestion.getItems()) {
                quizTextBuilder.append("Question: ").append(question.getContent()).append("\n");
                List<Option> options = serviceOption.getOptionByQuestion(question.getQuestionId());
                for (Option option : options) {
                    quizTextBuilder.append(option.getOption_content()).append("\n");
                }
            }
            String quizText = quizTextBuilder.toString();

            // Générer le code QR
            BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(quizText, 300, 300);

            // Enregistrer l'image ou effectuer d'autres opérations nécessaires

            // Enregistrer l'image ou effectuer d'autres opérations nécessaires
            File qrCodeFile = new File("quiz_qr_code.png");
            ImageIO.write(qrCodeImage, "PNG", qrCodeFile);
            System.out.println("Code QR généré avec succès!");
            displayQRCodeInNewWindow(qrCodeFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void displayQRCodeInNewWindow(String filePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeQR.fxml"));
            Parent root = loader.load();
            CodeQRController controller = loader.getController();
            controller.displayQRCode(filePath);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("QR Code Display");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("ChatGPT");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        }
    }
}
