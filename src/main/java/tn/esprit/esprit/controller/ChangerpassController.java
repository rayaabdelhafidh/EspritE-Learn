package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ChangerpassController {
    @FXML
    private TextField confiramtiondumotdupasseTexte;

    @FXML
    private TextField motdepasseTexte;

    @FXML
    private TextField nouveaumotdepasseTexte;

    @FXML
    private Button retourf;
    @FXML
    private void rt(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEtudiant.fxml"));
            Parent root = loader.load();
            retourf.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
