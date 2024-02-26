package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.Models.Enum.Bloc;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;

public class AjouterSalle {

    ObservableList<Bloc> blocList = FXCollections.observableArrayList(Bloc.values());

    @FXML
    private TextField numSalle;

    @FXML
    private ComboBox<Bloc> bloc;

    @FXML
    private Button ajoutSalle;

    private final ServiceSalle ss = new ServiceSalle();

    //(int salleId, Bloc bloc, int numeroSalle)
    @FXML
    void ajouterSalle(ActionEvent event) {
        ss.ajouterSalle(new Salle(bloc.getValue(),Integer.parseInt(numSalle.getText())));

    }
    @FXML
    void initialize() {
        bloc.setItems(blocList);
        assert numSalle != null : "fx:id=\"numSalle\" was not injected: check your FXML file 'AjouterSalle.fxml'.";

        assert bloc != null : "fx:id=\"bloc\" was not injected: check your FXML file 'AjouterSalle.fxml'.";


    }
}
