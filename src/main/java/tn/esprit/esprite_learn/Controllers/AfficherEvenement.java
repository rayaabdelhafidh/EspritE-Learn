package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class AfficherEvenement {

    @FXML
    private Button AfficherBtn;

    @FXML
    private Button ModifierBtn;

    @FXML
    private Button SupprimerBtn;

    @FXML
    private ListView<String> detailsList;

    @FXML
    private ListView<String> eventList;

    @FXML
    private TitledPane pane;

    @FXML
    private ImageView image;

    @FXML
    void AfficherEvenement(ActionEvent event) throws SQLException {
        DataBase db = DataBase.getInstance();
        ServiceEvenement se = new ServiceEvenement();
        ArrayList<Evenement> evenements = se.display();
        // Clear existing items
        eventList.getItems().clear();

        for (Evenement evenmt : evenements) {
            String name = evenmt.getNomEvenement();
            System.out.println("Event Name: " + name);
            eventList.getItems().add(name);
        }

    }
    public void initialize() {
        // Add context menu to the clubView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem showMenuItem = new MenuItem("Show");
        showMenuItem.setOnAction(this::ShowDetails);
        contextMenu.getItems().add(showMenuItem);
        eventList.setContextMenu(contextMenu);
    }
    @FXML
    void ShowDetails(ActionEvent event){
        Evenement selectedClub = onSelectedItem();
        Image defaultImage=new Image("C:/Users/abdel/OneDrive/Bureau/1.png");
        // Display details in the detailsView
        detailsList.getItems().clear();
        detailsList.getItems().add("ID de l'evenement: " + selectedClub.getIdEvenement());
        detailsList.getItems().add("Nom de l'evenement: " + selectedClub.getNomEvenement());
        detailsList.getItems().add("Date de l'evenement: " + selectedClub.getDateEvenement());
        detailsList.getItems().add("Lieu de l'evenement: " + selectedClub.getLieuEvenement());
        detailsList.getItems().add("Club responsable:  " + selectedClub.getClub());
        // Rest of the code remains unchanged
        String url = selectedClub.getAfficheEvenement();
        String imageURL=normalizePath(url);
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                // Try to create the Image object from the URL
                Image image1 = new Image(imageURL);
                image.setImage(image1);
            } catch (Exception e) {
                // Handle the case where the URL is invalid or resource not found
                // You can set a default image or hide the ImageView
                image.setImage(defaultImage);  // Set a default image
            }
        } else {
            // Handle the case where there is no image URL (optional)
            image.setImage(defaultImage);
        }
        }


    public Evenement onSelectedItem() {
        String selectedEventName = eventList.getSelectionModel().getSelectedItem();
        try {
            DataBase db = DataBase.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceEvenement sc = null;
        try {
            sc = new ServiceEvenement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Evenement> evenements = sc.display();
        Evenement selectedEvent = null;
        if (selectedEventName != null) {
            // Retrieve details based on the selected club name
            selectedEvent = sc.find(selectedEventName);

        }
        return selectedEvent;
    }

    @FXML
    void ModifierEvenement(ActionEvent event) {

    }

    @FXML
    void SupprimerEvenement(ActionEvent event) {
        String selectedEvenement = eventList.getSelectionModel().getSelectedItem();
        try {
            DataBase db = DataBase.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceEvenement sc = null;
        try {
            sc = new ServiceEvenement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Evenement> evenements = sc.display();
        Evenement ev = null;
        if (selectedEvenement != null) {
            // Retrieve details based on the selected club name
            ev = sc.find(selectedEvenement);
            sc.delete(ev);
            detailsList.getItems().clear();
            image.setImage(null);
        }

    }

        private String normalizePath(String originalPath) {
            // Create a Path object from the original path
            Path path = Paths.get(originalPath);

            // Normalize the path
            Path normalizedPath = path.normalize();

            // Convert the normalized path to a string
            return normalizedPath.toString();
        }

}
