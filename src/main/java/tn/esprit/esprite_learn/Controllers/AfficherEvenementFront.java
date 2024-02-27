package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherEvenementFront {

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
    void ShowDetails(ActionEvent event) {
        Evenement selectedClub = onSelectedItem();
        Image defaultImage=new Image("C:/Users/abdel/OneDrive/Bureau/1.png");
        detailsList.getItems().clear();
        detailsList.getItems().add("ID de l'evenement: " + selectedClub.getIdEvenement());
        detailsList.getItems().add("Nom de l'evenement: " + selectedClub.getNomEvenement());
        detailsList.getItems().add("Date de l'evenement: " + selectedClub.getDateEvenement());
        detailsList.getItems().add("Lieu de l'evenement: " + selectedClub.getLieuEvenement());
        detailsList.getItems().add("Club responsable:  " + selectedClub.getClub());
        String url = selectedClub.getAfficheEvenement();
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
            selectedEvent = sc.find(selectedEventName);

        }
        return selectedEvent;
    }

    private String normalizePath(String originalPath) {
        Path path = Paths.get(originalPath);
        Path normalizedPath = path.normalize();
        // converti l normalized path l string
        return normalizedPath.toString();
    }

    void show(Clubs c) throws SQLException {
        ServiceEvenement sc = new ServiceEvenement();
        ArrayList<Evenement> evenements = new ArrayList<>();
        evenements = sc.findByClub(c);
        for (Evenement selectedClub : evenements) {
            Image defaultImage = new Image("C:/Users/abdel/OneDrive/Bureau/1.png");
            detailsList.getItems().clear();
            detailsList.getItems().add("ID de l'evenement: " + selectedClub.getIdEvenement());
            detailsList.getItems().add("Nom de l'evenement: " + selectedClub.getNomEvenement());
            detailsList.getItems().add("Date de l'evenement: " + selectedClub.getDateEvenement());
            detailsList.getItems().add("Lieu de l'evenement: " + selectedClub.getLieuEvenement());
            detailsList.getItems().add("Club responsable:  " + selectedClub.getClub());
            String url = selectedClub.getAfficheEvenement();
            String imageURL = normalizePath(url);
            if (imageURL != null && !imageURL.isEmpty()) {
                try {
                    // Try to create the Image object from the URL
                    Image image1 = new Image(imageURL);
                    image.setImage(image1);
                } catch (Exception e) {
                    // fel cas enou l url invalide
                    image.setImage(defaultImage);
                }
            } else {
                //houni mafamech url aslan
                image.setImage(defaultImage);
            }
        }
    }
}
