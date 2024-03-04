package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class EvenementController {
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

    public EvenementController() throws SQLException {
    }

    public void initialize() {
    }

    public void data(Evenement ev){
        Image defaultImage;
        defaultImage = new Image("file:///C:/Users/abdel/OneDrive/Bureau/126.png");
        String url = ev.getAfficheEvenement();
        //Clubs c=scc.findbyId(ev.getClub());
        String imageURL=normalizePath(url);
        if (imageURL != null && !imageURL.isEmpty()) {
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

    @FXML
    void AfficherEvenementFront(ActionEvent event) throws SQLException {
        ServiceEvenement se=new ServiceEvenement();
        String nom=Nom.getText();
        Evenement e=se.chercherEvenement(nom);
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
        assert root != null;
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Evenements!");
        newStage.setScene(scene);
        newStage.show();
    }
}
