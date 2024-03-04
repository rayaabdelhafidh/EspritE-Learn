package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class interfaceUtil {

    @FXML
    private Button adminBtn;

    @FXML
    private Button etudiantBtn;

    @FXML
    void AfficherClub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClub.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Clubs!");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading AfficherClub.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void AfficherClubFront(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClubFront.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Clubs!");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading AfficherClubFront.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
