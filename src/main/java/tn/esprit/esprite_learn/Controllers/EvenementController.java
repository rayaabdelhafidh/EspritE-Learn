package tn.esprit.esprite_learn.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

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
        Image defaultImage=new Image("C:/Users/abdel/OneDrive/Bureau/1.png");
        String url = ev.getAfficheEvenement();
        Clubs c=scc.findbyId(ev.getClub());
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

}
