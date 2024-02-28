package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProfileEnseignantController {
    @FXML
    private Button cpass;

    @FXML
    private TextField emailf;


    @FXML
    private TextField mdpf;

    @FXML
    private Button modif;

    @FXML
    private TextField nomf;
    @FXML
    private Button logout;
    @FXML
    private void GoToLogin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            logout.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
