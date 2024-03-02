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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficherEvenementFront implements Initializable {

    @FXML
    private Button AfficherBtn;
    @FXML
    private ListView<String> detailsList;
    @FXML
    private ListView<String> eventList;
    @FXML
    private ImageView image;
    @FXML
    private TitledPane pane;
    @FXML
    private MenuItem show;
    @FXML
    private GridPane EventContainer;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;
    ServiceClub scc= new ServiceClub();

    @FXML
    void ChercherEvenement(ActionEvent event) throws SQLException {
        ServiceEvenement se=new ServiceEvenement();
        String nom=search.getText();
        Evenement e=se.chercherEvenement(nom);
        EventContainer.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/DetailsEvenements.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            System.out.println("Error loading DetailsEvenements.fxml: " + ex.getMessage());
            ex.printStackTrace();
        }
        DetailsEvenements controller = fxmlLoader.getController();
        controller.data(e);
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Evenements!");
        newStage.setScene(scene);
        newStage.show();
    }

    public AfficherEvenementFront() throws SQLException {
    }


    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;
        try {
            ServiceEvenement sc = new ServiceEvenement();
            ArrayList<Evenement> evenements = sc.display();
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

    private String normalizePath(String originalPath) {
        Path path = Paths.get(originalPath);
        Path normalizedPath = path.normalize();
        // converti l normalized path l string
        return normalizedPath.toString();
    }

    @FXML
    void AfficherClubFront(ActionEvent event) {

    }

}
