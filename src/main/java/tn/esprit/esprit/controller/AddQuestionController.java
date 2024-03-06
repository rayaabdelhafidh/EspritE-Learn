package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuestion;

import java.io.IOException;

public class AddQuestionController {

    private ServiceQuestion qq=new ServiceQuestion();
    AfficheQuizController afficheQuizController=new AfficheQuizController();

    private int quiz_id; // L'ID du quiz auquel la question sera associée
    private ServiceQuestion serviceQuestion; // Service pour gérer les opérations sur les questions

    private Quiz quiz;

    // Setter pour l'objet Quiz
    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    @FXML
    private TextField contenuTF;
    @FXML
    private TextField scoreTF;
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void AjouterQuestion() {
        String content = contenuTF.getText().trim();
        int score;
        try {
            score= Integer.parseInt(scoreTF.getText().trim());
        }
        catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Le score doit être un entier.");
            return; // Sortir de la méthode si le score n'est pas un entier
        }



        if (content.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez saisir le contenu de la question.");
            return; // Sortir de la méthode si le champ est vide
        }
        // Créez une nouvelle instance de Question avec le contenu et l'ID du quiz
        Question question = new Question();
        question.setContent(content);

        Quiz quiz = new Quiz();
        quiz.setQuiz_id(quiz_id); // Utilisez l'ID du quiz passé
        question.setQuiz(quiz);
        question.setScore(score);
        // Appelez la méthode add(question) du service ServiceQuestion pour ajouter la question à la base de données
        ServiceQuestion serviceQuestion = new ServiceQuestion();
        serviceQuestion.add(question);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheQuestion.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de l'interface afficheQuestion.fxml
            AfficheQuestionController controller = loader.getController();

            // Vous pouvez passer des données au contrôleur si nécessaire


            // Obtenez la scène actuelle et remplacez son contenu par la nouvelle interface chargée
            Scene scene = new Scene(root);
            Stage stage = (Stage) contenuTF.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de l'interface
        }
    }





}
