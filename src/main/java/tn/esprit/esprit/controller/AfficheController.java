package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficheController implements Initializable {
    @FXML
    private ListView<Quiz> listView;
    @FXML
    private Button btt;
    private ServiceQuiz serviceQuiz=new ServiceQuiz();
    private ServiceQuestion serviceQuestion=new ServiceQuestion();
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Chargez les données depuis le service et ajoutez-les à la table
        ArrayList<Quiz> quizzes = serviceQuiz.getAll();
        listView.getItems().addAll(quizzes);
        btt.setOnAction(event -> {
            { // Vérifie si un élément est cliqué une fois
                Quiz quiz = listView.getSelectionModel().getSelectedItem(); // Récupère l'objet Quiz sélectionné
                if (quiz != null) {
                    int quiz_id = quiz.getQuiz_id(); // Récupère l'ID du quiz

                    System.out.println("ID du quiz sélectionné : " + quiz_id);
                    openAffiche(quiz_id);


                }
            }
        });


    }

    private void openAffiche(int quiz_id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheReponse.fxml"));
            Parent root = loader.load();
            AfficheReponseController controller=loader.getController();

            controller.setQuiz_id(quiz_id);
            controller.setServiceQuestion(serviceQuestion);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions List");
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
