package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifierSalle implements Initializable {

    @FXML
    private Button modifier;

    @FXML
    private TextField idSalle;

    @FXML
    private ComboBox<Bloc> newBloc;


    @FXML
    private TextField newNum;

    ObservableList<Bloc> blocList = FXCollections.observableArrayList(Bloc.values());
    private final ServiceSalle ss = new ServiceSalle();

    @FXML
    void modifierSalle(ActionEvent event) {
        if (idSalle.getText().isEmpty() || newBloc.getValue() == null || newNum.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs, s'il vous plaît.");
            alert.showAndWait();
        } else {
            int salleId = Integer.parseInt(idSalle.getText());
            Bloc bloc = newBloc.getValue();
            int numeroSalle = Integer.parseInt(newNum.getText());
            Salle salle = new Salle();
            salle.setSalleId(salleId);
            salle.setBloc(bloc);
            salle.setNumeroSalle(numeroSalle);
            ss.modifierSalle(salle);
            System.out.println("Salle modifiée.");
        } }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newBloc.setItems(blocList);
    }
}
