package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Services.ServiceOption;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddOptionController  {
    public void setQuestion_id(int questionId ) {
        this.questionId = questionId;
    }

    @FXML
    private TextField IsCorrectTF;

    @FXML
    private TextField optionTF;
    private Question question;
    AfficheQuestionController afficheQuestionController=new AfficheQuestionController();
    ServiceOption oo=new ServiceOption();
    private int questionId;
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void AjouterOption(ActionEvent event) {
        String option_content = optionTF.getText().trim();
        String is_correct_text = IsCorrectTF.getText().trim();

        if (option_content.isEmpty() || is_correct_text.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez remplir tous les champs.");
            return;
        }

        Boolean is_correct;
        if (!is_correct_text.equalsIgnoreCase("true") && !is_correct_text.equalsIgnoreCase("false")) {
            showAlert("Erreur de saisie", "La valeur de 'IsCorrect' doit être 'true' ou 'false'.");
            return;
        } else {
            is_correct = Boolean.parseBoolean(is_correct_text);
        }
        Option option=new Option();
        option.setOption_content(option_content);
        option.setIs_correct(is_correct);
        Question question=new Question();
        question.setQuestionId(questionId);
        option.setQuestion(question);
        ServiceOption serviceOption=new ServiceOption();
        serviceOption.add(option);
        optionTF.clear();
        IsCorrectTF.clear();

    }
    @FXML
    void afficherOption(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOption.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon vos besoins
        }
    }
}
