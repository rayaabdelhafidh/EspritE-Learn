package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class AfficherEvenement implements Initializable {

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

    ServiceClub scc=new ServiceClub();

    Evenement e;

    public AfficherEvenement() throws SQLException {
    }

    @FXML
    void ShowDetails(ActionEvent event){
        Evenement selectedClub = onSelectedItem();
        Clubs cl=scc.findbyId(selectedClub.getClub());
        Image defaultImage=new Image("file:///C:/Users/abdel/OneDrive/Bureau/126.png");
        detailsList.getItems().clear();
        detailsList.getItems().add("Nom de l'evenement: " + selectedClub.getNomEvenement());
        detailsList.getItems().add("Date de l'evenement: " + selectedClub.getDateEvenement());
        detailsList.getItems().add("Prix pass: " + selectedClub.getPrixEvenement());
        detailsList.getItems().add("Lieu de l'evenement: " + selectedClub.getLieuEvenement());
        detailsList.getItems().add("Club responsable:  " + cl.getNomClub());
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

    @FXML
    void ModifierEvenement(ActionEvent event) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/ModifierEvenement.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading ModifierClub.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        ModifierEvenement controller = fxmlLoader.getController();
        e=onSelectedItem();
        controller.setEvenement(e);
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        currentStage.close(); // Close the current stage
        Stage newStage = new Stage();
        newStage.setTitle("Modifier Evenement!");
        newStage.setScene(scene);
        newStage.show();
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
            ev = sc.find(selectedEvenement);
            sc.delete(ev);
            detailsList.getItems().clear();
            image.setImage(null);
        }

    }

        private String normalizePath(String originalPath) {
            Path path = Paths.get(originalPath);
            Path normalizedPath = path.normalize();
            return normalizedPath.toString();
        }

        void show(Clubs c) throws SQLException {
            ServiceEvenement sc=new ServiceEvenement();
            ArrayList<Evenement> evenements= new ArrayList<>();
            evenements= sc.findByClub(c);
            for(Evenement selectedClub:evenements) {
                Image defaultImage = new Image("file:///C:/Users/abdel/OneDrive/Bureau/126.png");
                Clubs cl=scc.findbyId(selectedClub.getClub());
                detailsList.getItems().clear();
                detailsList.getItems().add("Nom de l'evenement: " + selectedClub.getNomEvenement());
                detailsList.getItems().add("Date de l'evenement: " + selectedClub.getDateEvenement());
                detailsList.getItems().add("Prix pass: " + selectedClub.getPrixEvenement());
                detailsList.getItems().add("Lieu de l'evenement: " + selectedClub.getLieuEvenement());
                detailsList.getItems().add("Club responsable:  " + cl.getNomClub());
                String url = selectedClub.getAfficheEvenement();
                String imageURL = normalizePath(url);
                if (imageURL != null && !imageURL.isEmpty()) {
                    try {
                        Image image1 = new Image(imageURL);
                        image.setImage(image1);
                    } catch (Exception e) {
                        image.setImage(defaultImage);
                    }
                } else {
                    image.setImage(defaultImage);
                }
            }
        }
    @FXML
    void AjouterEvenement(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AjoutEvenement.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Ajouter Evenement!");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem showMenuItem = new MenuItem("Show");
        showMenuItem.setOnAction(this::ShowDetails);
        contextMenu.getItems().add(showMenuItem);
        eventList.setContextMenu(contextMenu);
        try {
            DataBase db = DataBase.getInstance();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        ServiceEvenement se = null;
        try {
            se = new ServiceEvenement();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        ArrayList<Evenement> evenements = se.display();
        // Clear existing items
        eventList.getItems().clear();

        for (Evenement evenmt : evenements) {
            String name = evenmt.getNomEvenement();
            System.out.println("Event Name: " + name);
            eventList.getItems().add(name);
        }
    }
}
