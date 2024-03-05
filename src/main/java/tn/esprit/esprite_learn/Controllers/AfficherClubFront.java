package tn.esprit.esprite_learn.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class AfficherClubFront implements Initializable {
    @FXML
    private GridPane ClubContainer;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;

    @FXML
    void AfficherEvenementFront(ActionEvent event) throws SQLException {
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
    void ChercherClub(ActionEvent event) throws SQLException {
        ServiceClub se=new ServiceClub();
        String nom=search.getText();
        Clubs e=se.ChercherClub(nom);
        ClubContainer.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/DetailsClub.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            System.out.println("Error loading DetailsClubs.fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
        DetailsClub controller = fxmlLoader.getController();
        controller.data(e);
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Clubs!");
        newStage.setScene(scene);
        newStage.show();
    }
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;
        try {
            ServiceClub sc = new ServiceClub();
            ArrayList<Clubs> clubs = sc.display();
            for (Clubs club: clubs) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/Club.fxml"));
                VBox eventBox = fxmlLoader.load();
                ClubController controller = fxmlLoader.getController();
                controller.data(club);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                ClubContainer.add(eventBox, column++, row);
                GridPane.setMargin(eventBox, new Insets(10));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void retour(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/interfaceUtil.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Dashboard!");
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Error loading interfaceUtil.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void Tri(ActionEvent event) {
        int column = 0;
        int row = 1;
        try {
            ServiceClub sc = new ServiceClub();
            ArrayList<Clubs> clubs = sc.TriClubs();
            for (Clubs club: clubs) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/Club.fxml"));
                VBox eventBox = fxmlLoader.load();
                ClubController controller = fxmlLoader.getController();
                controller.data(club);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                ClubContainer.add(eventBox, column++, row);
                GridPane.setMargin(eventBox, new Insets(10));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
