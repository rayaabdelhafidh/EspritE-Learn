package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileEtudiantController implements Initializable {
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
    private final ServiceUser su = new ServiceUser();

    private User user;
    private int id;

    @FXML
    private void GoTocpass(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changerpass.fxml"));
            Parent root = loader.load();
            cpass.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }






    @FXML
    private void UpdateProfilEtudiant(ActionEvent event) throws IOException, SQLException {
        if (user != null) {
            // Get the updated information from the text fields
            String updatedNom = nomf.getText();
            String updatedEmail = emailf.getText();

            // Update the user object with the new information
            user.setNom(updatedNom);
            user.setEmail(updatedEmail);

            // Call the service method to update the user information in the database
            boolean updated = su.modifier(user);
            if (updated) {
                // Show a success message
                showAlert(Alert.AlertType.INFORMATION, "Success", "User information updated successfully.");
            } else {
                // Show a failure message
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user information.");
            }
        } else {
            System.err.println("User is null");
        }
    }










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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = fxmlLoader.load();
            loginController loginController = fxmlLoader.getController();
            if (loginController != null) {
                this.id = loginController.idSession;
                // Call the method to retrieve user information by ID
                user = su.getUserById(this.id);
                if (user != null) {
                    // Assuming nomf and emailf are components in your UI
                    nomf.setText(user.getNom()); // Set the text of a component
                    emailf.setText(user.getEmail()); // Set the text of another component
                } else {
                    System.err.println("User not found");
                }
            } else {
                System.err.println("Controller is null");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
