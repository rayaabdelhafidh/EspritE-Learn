package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceOption;
import tn.esprit.Services.ServiceQuestion;

public class UpdateQuestionController {

    @FXML
    private TextField contTF;
    @FXML
    private TextField scoreTF;

    @FXML
    private Button updateButton;
    private Question selectedQuestion;
    private ServiceQuestion serviceQuestion;
    Quiz quiz=new Quiz();
    public void setData(Question question) {
        this.selectedQuestion = question;
        contTF.setText(question.getContent());
        scoreTF.setText(String.valueOf(question.getScore()));


    }

    @FXML
    void updateQuestion() {
        ServiceQuestion serviceQuestion1=new ServiceQuestion();
        // Mettre à jour les détails de l'option avec les valeurs des champs de texte

        selectedQuestion.setContent(contTF.getText());
        selectedQuestion.setScore(Integer.parseInt(scoreTF.getText()));
        // Appeler la méthode de mise à jour du service avec l'option mise à jour
        serviceQuestion1.update(selectedQuestion);
        System.out.println("question mise à jour avec succès.");
        // Fermer la fenêtre de mise à jour

    }

}
