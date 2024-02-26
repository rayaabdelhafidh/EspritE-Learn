package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;
import tn.esprit.utils.MyDataBase;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficherSalles implements Initializable {

    private final ServiceSalle ss = new ServiceSalle();

    @FXML
    private ListView<Salle> listeSalles;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Salle> salles = ss.afficherTousSalles();
// Clear existing items in the ListView
        listeSalles.getItems().clear();

        // Add retrieved salles to the ListView
        listeSalles.getItems().addAll(salles);

        listeSalles.setCellFactory(new Callback<ListView<Salle>, ListCell<Salle>>() {
            @Override
            public ListCell<Salle> call(ListView<Salle> listView) {
                return new ListCell<Salle>() {
                    @Override
                    protected void updateItem(Salle salle, boolean empty) {
                        super.updateItem(salle, empty);
                        if (salle != null && !empty) {
                            // Customize the cell display here
                            setText("Bloc: " + salle.getBloc() + "\nNuméro Salle: " + salle.getNumeroSalle());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });



    }


}
