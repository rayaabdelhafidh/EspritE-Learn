package tn.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class AjouterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField optionTF;

    @FXML
    private TextField questionTF;

    @FXML
    private TextField titleTF;
    @FXML
    void AjouterOption(ActionEvent event) {

    }

    @FXML
    void AjouterQuestion(ActionEvent event) {

    }

    @FXML
    void AjouterQuiz(ActionEvent event) {

    }
    @FXML
    void initialize() {
        assert optionTF != null : "fx:id=\"optionTF\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert questionTF != null : "fx:id=\"questionTF\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert titleTF != null : "fx:id=\"titleTF\" was not injected: check your FXML file 'Ajouter.fxml'.";

    }

}