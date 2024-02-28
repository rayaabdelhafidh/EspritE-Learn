package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class usercontroller implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button dadhbord;
    @FXML
    private Button btnEn;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane listUsers;


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
    private void GoTodashbord(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashbordAdmin.fxml"));
            Parent root = loader.load();
            logout.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void gotoenseignant(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Enseignant.fxml"));
            Parent root = loader.load();
            logout.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceUser as = new ServiceUser();
            List<User> act = as.getAll();
            for (int i = 0; i < act.size(); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/itemUser.fxml"));
                    listUsers = fxmlLoader.load();
                    itemUserController itemController = fxmlLoader.getController();
                    itemController.setData(act.get(i));
                    HBox hbox = new HBox(); // Create a new HBox instance
                    hbox.getChildren().add(listUsers);
                    vBox.getChildren().add(hbox); // Adding the new hbox to vBox
                } catch (IOException ex) {
                    Logger.getLogger(itemUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
