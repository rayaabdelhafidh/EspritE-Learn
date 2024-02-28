package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class DashbordAdminController {
    @FXML
    private Button logout;
    @FXML
    private Button btnu;


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
    @FXML
    private void gotouser(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user.fxml"));
            Parent root = loader.load();
            btnu.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
