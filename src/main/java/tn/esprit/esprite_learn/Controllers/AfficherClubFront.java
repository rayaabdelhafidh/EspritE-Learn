package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherClubFront {

    @FXML
    private Button AfficherBtn;

    @FXML
    private Button AfficherBtn1;

    @FXML
    private ListView<String> clubView;

    @FXML
    private ListView<String> detailsView;

    @FXML
    private TitledPane pane;

    @FXML
    private MenuItem show;

    Clubs c;

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
            // nkharej details basé aal selected club name
            selectedClub = sc.find(selectedClubName);

        }
        return selectedClub;
    }
    @FXML
    void AfficherClub(ActionEvent event) throws SQLException {
        DataBase db = DataBase.getInstance();
        ServiceClub sc = new ServiceClub();
        ArrayList<Clubs> clubs = sc.display();
        clubView.getItems().clear();

        for (Clubs club : clubs) {
            String name = club.getNomClub();
            System.out.println("Club Name: " + name);
            clubView.getItems().add(name);
        }

    }

    @FXML
    void AfficherEvenementclub(ActionEvent event) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenementFront.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error loading AfficherEvenement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        AfficherEvenementFront controller = fxmlLoader.getController();
        c=onSelectedItem();
        // naadi les détails mel haja eli selectionnitha fel menu lel contrôleur
        //controller.show(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Afficher");
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        // najouti context menu ll clubView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem showMenuItem = new MenuItem("Show");
        showMenuItem.setOnAction(this::ShowDetails);
        contextMenu.getItems().add(showMenuItem);
        clubView.setContextMenu(contextMenu);
    }
    @FXML
    void ShowDetails(ActionEvent event) {
        Clubs selectedClub = onSelectedItem();
        // naffichi detailet fi detailsView
        detailsView.getItems().clear();
        detailsView.getItems().add("Nom du club: " + selectedClub.getNomClub());
        detailsView.getItems().add("Date de Fondation: " + selectedClub.getDateFondation());
        detailsView.getItems().add("Type d'activité: " + selectedClub.getTypeActivite());
        detailsView.getItems().add("De quoi il s'agit? " + selectedClub.getDescription());
        detailsView.getItems().add("Encore actif? " + selectedClub.isActive());
    }

}
