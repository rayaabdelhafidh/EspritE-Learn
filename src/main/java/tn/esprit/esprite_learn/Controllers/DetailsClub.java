package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class DetailsClub {
    @FXML
    private GridPane EventContainer;
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
    private Button searchBtn;

    public void data(Clubs c){
        Nom.setText(c.getNomClub());
        Date.setText("" + c.getDateFondation());
        Description.setText(c.getDescription());
        int column = 0;
        int row = 1;
        try {
            ServiceEvenement sc = new ServiceEvenement();
            ArrayList<Evenement> evenements = sc.findByClub(c);
            for (Evenement evenement : evenements) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/Evenement.fxml"));
                VBox eventBox = fxmlLoader.load();
                EvenementController controller = fxmlLoader.getController();
                controller.data(evenement);
                if (column == 6) {
                    column = 0;
                    ++row;
                }
                EventContainer.add(eventBox, column++, row);
                GridPane.setMargin(eventBox, new Insets(10));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void AfficherClubFront(ActionEvent event) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenementFront.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading AfficherEvenement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        AfficherEvenementFront controller = fxmlLoader.getController();
        ServiceEvenement se=new ServiceEvenement();
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Evenements!");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void revenirEvenements(ActionEvent event) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClubFront.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading AfficherClubFront.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        AfficherClubFront controller = fxmlLoader.getController();
        ServiceClub se=new ServiceClub();
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Evenements!");
        newStage.setScene(scene);
        newStage.show();
    }

}