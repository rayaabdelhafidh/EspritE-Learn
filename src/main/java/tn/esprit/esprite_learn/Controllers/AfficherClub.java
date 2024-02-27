package tn.esprit.esprite_learn.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherClub {

    @FXML
    private Button AfficherBtn;

    @FXML
    private Button ModifierBtn;

    @FXML
    private Button SupprimerBtn;

    @FXML
    private Button SupprimerBtn1;

    @FXML
    private ListView<String> clubView;

    @FXML
    private ListView<String> detailsView;

    @FXML
    private TitledPane pane;

    @FXML
    private MenuItem show;

    private Clubs c;

    Evenement e;

    public AfficherClub() {
    }

    public void AfficherClub(ActionEvent event) throws SQLException {
        DataBase db = DataBase.getInstance();
        ServiceClub sc = new ServiceClub();
        ArrayList<Clubs> clubs = sc.display();
        clubView.getItems().clear();

        for (Clubs club : clubs) {
            String name = club.getNomClub();
            System.out.println("Club Name: " + name);
            clubView.getItems().add(name);
        }
        clubView.getItems().add("jhdjdh");

    }

    @FXML
    void AjoutClub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AjoutClub.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void SupprimerClub(ActionEvent event) {
        String selectedClubName = clubView.getSelectionModel().getSelectedItem();
        try {
            DataBase db = DataBase.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceClub sc = null;
        try {
            sc = new ServiceClub();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Clubs> clubs = sc.display();
        Clubs selectedClub = null;
        if (selectedClubName != null) {
            selectedClub = sc.find(selectedClubName);
            sc.delete(selectedClub);
            detailsView.getItems().clear();


        }

    }

    @FXML
    void UpdateClub(ActionEvent event) {

    }


    @FXML
    void ModifierClub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/ModifierClub.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading ModifierClub.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        ModifierClub controller = fxmlLoader.getController();
        c=onSelectedItem();
        controller.setClub(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Modifier");
        stage.setScene(scene);
        stage.show();
    }

   public void initialize() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem showMenuItem = new MenuItem("Show");
        showMenuItem.setOnAction(this::ShowDetails);
        contextMenu.getItems().add(showMenuItem);
        clubView.setContextMenu(contextMenu);
    }
   @FXML
    void ShowDetails(ActionEvent event){
            Clubs selectedClub = onSelectedItem();

            detailsView.getItems().clear();
           detailsView.getItems().add("ID du club: " + selectedClub.getIdClub());
            detailsView.getItems().add("Nom du club: " + selectedClub.getNomClub());
            detailsView.getItems().add("Date de Fondation: " + selectedClub.getDateFondation());
            detailsView.getItems().add("Type d'activité: " + selectedClub.getTypeActivite());
            detailsView.getItems().add("De quoi il s'agit? " + selectedClub.getDescription());
            detailsView.getItems().add("Encore actif? " + selectedClub.isActive());
        }


    public Clubs onSelectedItem() {
        String selectedClubName = clubView.getSelectionModel().getSelectedItem();
        try {
            DataBase db = DataBase.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceClub sc = null;
        try {
            sc = new ServiceClub();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Clubs> clubs = sc.display();
        Clubs selectedClub = null;
        if (selectedClubName != null) {
            selectedClub = sc.find(selectedClubName);

        }
        return selectedClub;
    }

    @FXML
    void AfficherEvenementclub(ActionEvent event) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenement.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading AfficherEvenement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        AfficherEvenement controller = fxmlLoader.getController();
        c=onSelectedItem();
        controller.show(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Evenements");
        stage.setScene(scene);
        stage.show();
    }
}