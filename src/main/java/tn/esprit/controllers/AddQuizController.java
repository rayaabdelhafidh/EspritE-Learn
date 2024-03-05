package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuiz;

import java.io.IOException;

public class AddQuizController {

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TextField MatiereTF;


     private final ServiceQuiz qz=new ServiceQuiz();
    @FXML
    void AjouterQuiz(ActionEvent event) {
        String description = DescriptionTF.getText().trim();
        String matiere = MatiereTF.getText().trim();


        // Vérification si les champs sont vides
        if (description.isEmpty() || matiere.isEmpty() ) {
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
            return; // Sortir de la méthode si un champ est vide
        }

        // Vérification si l'ID de l'enseignant est un entier


        // Ajouter le quiz s'il passe toutes les vérifications
        qz.add(new Quiz(0, description, matiere));
        DescriptionTF.clear();

        MatiereTF.clear();
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void afficherQuiz(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheQuiz.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        }
    }

    // Vos autres méthodes et champs
}



