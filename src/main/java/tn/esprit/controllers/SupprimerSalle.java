package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;

public class SupprimerSalle {

    @FXML
    private TextField idSalle;

    @FXML
    private Button supprimer;

    private final ServiceSalle ss = new ServiceSalle();

    @FXML
    void supprimerSalle(ActionEvent event) {
        if (idSalle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir l'identifiant de la salle à supprimer.");
            alert.showAndWait();
        } else {
            int salleId = Integer.parseInt(idSalle.getText());
            Salle salle = new Salle();
            salle.setSalleId(salleId);
            boolean isDeleted = ss.supprimerSalle(salle);
            if (isDeleted) {
                System.out.println("Salle " + salleId + " est supprimée.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Impossible de supprimer la salle " + salleId + ".");
                alert.showAndWait();
            }
        }
} }
