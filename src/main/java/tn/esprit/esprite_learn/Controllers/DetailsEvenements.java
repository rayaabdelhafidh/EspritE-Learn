package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

public class DetailsEvenements {

    @FXML
    private GridPane EventContainer;

    @FXML
    private Button searchBtn;

    @FXML
    private Label Date;

    @FXML
    private Label Lieu;

    @FXML
    private Label Nom;

    @FXML
    private Label Prix;

    @FXML
    private ImageView image;

    ServiceClub scc= new ServiceClub();

    public DetailsEvenements() throws SQLException {
    }

    @FXML
    void AfficherClubFront(ActionEvent event) {
    }

    @FXML
    void revenirEvenements(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenementFront.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage

            Stage newStage = new Stage();
            newStage.setTitle("Evenements!");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void data(Evenement ev){
        Image defaultImage=new Image("C:/Users/abdel/OneDrive/Bureau/1.png");
        String url = ev.getAfficheEvenement();
        Clubs c=scc.findbyId(ev.getClub());
        String imageURL=normalizePath(url);
        if (!imageURL.isEmpty()) {
            try {
                Image image1 = new Image(imageURL);
                image.setImage(image1);
            } catch (Exception e) {
                image.setImage(defaultImage);  // Set a default image
            }
        } else {
            image.setImage(defaultImage);
        }
        Nom.setText(ev.getNomEvenement());
        Date.setText(""+ev.getDateEvenement());
        Lieu.setText(ev.getLieuEvenement());
        Prix.setText(ev.getPrixEvenement()+"dt");
    }


    private String normalizePath(String originalPath) {
        if (originalPath == null) {
            // Provide a default image path or handle accordingly
            return "default_image_path.png";
        }
        Path path = Paths.get(originalPath);
        Path normalizedPath = path.normalize();
        // converti l normalized path l string
        return normalizedPath.toString();
    }


}
