
package tn.esprit.esprit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import tn.esprit.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.esprit.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Khalil
 */
public class ModifierMdpController implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private PasswordField mdp1;
    @FXML
    private PasswordField mdp2;
    String email2;
    ServiceUser us = new ServiceUser();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void updateMdp(String email) {
        email2 = email;

    }

    @FXML
    private void Update_password(ActionEvent event) throws SQLException, IOException {
        if (mdp1.getText().equals(mdp2.getText())) {
            us.ModifMDP(email2, mdp1.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("votre mot de passe a été changé avec succés");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();


        } else {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("champ vide ou les mot de passe ne sont pas identiques");
            alert.show();


        }


    }


}