package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

public class UpdateQuizController {

    @FXML
    private TextField decTF;

    @FXML
    private Button updateButton;
    private Quiz selectedQuiz;
    private ServiceQuiz serviceQuiz;
    Quiz quiz=new Quiz();
    public void setDat(Quiz quiz) {
        this.selectedQuiz = quiz;
        decTF.setText(quiz.getDescription());

    }


    @FXML
    void udateQuiz() {
        ServiceQuiz serviceQuiz1=new ServiceQuiz();
        // Mettre à jour les détails de l'option avec les valeurs des champs de texte

        selectedQuiz.setDescription(decTF.getText());
        // Appeler la méthode de mise à jour du service avec l'option mise à jour
        serviceQuiz1.update(selectedQuiz);
        System.out.println("quiz mise à jour avec succès.");
        // Fermer la fenêtre de mise à jour

    }

}