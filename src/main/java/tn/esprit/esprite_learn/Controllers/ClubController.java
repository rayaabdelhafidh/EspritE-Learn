package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;

import java.io.IOException;
import java.sql.SQLException;

public class ClubController {
    @FXML
    private Label Date;

    @FXML
    private Label Description;

    @FXML
    private Label Nom;

    @FXML
    private Label Type;

    @FXML
    private ImageView image;

    @FXML
    void AfficherClubFront(ActionEvent event) throws SQLException {
        ServiceClub se=new ServiceClub();
        String nom=Nom.getText();
        Clubs e=se.ChercherClub(nom);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/DetailsClub.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            System.out.println("Error loading DetailsClub.fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
        DetailsClub controller = fxmlLoader.getController();
        controller.data(e);
        assert root != null;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Clubs!");
        newStage.setScene(scene);
        newStage.show();
    }

    public void data(Clubs c) {
        Nom.setText(c.getNomClub());
        Date.setText("" + c.getDateFondation());
        Description.setText(c.getDescription());
    }

    public void initialize() {

    }

}
