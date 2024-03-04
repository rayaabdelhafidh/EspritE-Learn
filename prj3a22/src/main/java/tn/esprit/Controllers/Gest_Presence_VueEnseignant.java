package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.IServices.ServicePersonne;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Gest_Presence_VueEnseignant implements Initializable {

    @FXML
    private ListView<Personne> listViewEtudiants;

    @FXML
    private ListView<Presence> listViewAjout;

    @FXML
    private TextField nometudiant;

    @FXML
    private DatePicker dateAjoutPr;

    @FXML
    private ChoiceBox<String> ajoutSeance;

    @FXML
    private ChoiceBox<String> nomDesClasses;

    @FXML
    private ListView<Personne> affichageEtatPresence;

    @FXML
    private ComboBox<String> filtreClasse;

    @FXML
    private RadioButton radioAbsent;

    @FXML
    private RadioButton radioPresent;

    private ToggleGroup toggleGroup;

    private ServicePresence servicePresence;

    public Gest_Presence_VueEnseignant() {
        this.servicePresence = new ServicePresence();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        radioPresent.setToggleGroup(toggleGroup);
        radioAbsent.setToggleGroup(toggleGroup);

        ObservableList<String> presenceList = FXCollections.observableArrayList();
        for (Seance s : Seance.values()) {
            presenceList.add(s.toString());
        }
        ajoutSeance.setItems(presenceList);

        ServiceClasse scl = new ServiceClasse();
        ArrayList<String> classes = scl.getNomClasses();
        ObservableList<String> classeList = FXCollections.observableArrayList(classes);
        nomDesClasses.setItems(classeList);

        ServicePersonne SCP = new ServicePersonne();
        List<Personne> etudiants = SCP.getEtudiantsAvecPresence(String.valueOf(nomDesClasses));
        listViewEtudiants.getItems().addAll(etudiants);

        ServiceClasse serviceClasse = new ServiceClasse();
        ArrayList<String> classees = serviceClasse.getNomClasses();
        ObservableList<String> classeListe = FXCollections.observableArrayList(classes);
        filtreClasse.setItems(classeList);
    }

    @FXML
    void EnregistrerEtatPresence() {
        if (verifierSaisiesEnregistrerEtat()) {
            Personne etudiantSelectionne = listViewEtudiants.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = dateAjoutPr.getValue();
            Seance selectedSeance = ajoutSeance.getValue() != null ? Seance.valueOf(ajoutSeance.getValue()) : null;

            if (etudiantSelectionne != null && selectedDate != null && selectedSeance != null) {
                EtatPresence etatPresence = toggleGroup.getSelectedToggle().equals(radioAbsent) ? EtatPresence.Absent : EtatPresence.Present;
                ServicePersonne servicePersonne = new ServicePersonne();
                servicePersonne.enregistrerEtatPresence(etudiantSelectionne.getIdP(), etatPresence);
                showAlert(Alert.AlertType.INFORMATION, "Confirmation", "L'état de présence a été enregistré avec succès !");
            }
        }
    }

    @FXML
    public void CreatePresence() {
        if (!verifierSaisies()) {
            return;
        }
        String selectedClasseName = nomDesClasses.getValue();
        ServiceClasse serviceClasse = new ServiceClasse();
        classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);
        if (selectedClasse == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Classe non trouvée !");
            return;
        }
        LocalDate localDate = dateAjoutPr.getValue();
        if (localDate == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une date !");
            return;
        }
        Seance seance = ajoutSeance.getValue() != null ? Seance.valueOf(ajoutSeance.getValue()) : null;
        Presence presence = new Presence(localDate, seance, selectedClasse.getNomClasse());

        try {
            servicePresence.addPresence(presence);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la présence : " + e.getMessage());
            return;
        }
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "La présence est effectuée avec succès !");
    }

    private boolean verifierSaisies() {
        String selectedClasseName = nomDesClasses.getValue();

        if (selectedClasseName == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une classe !");
            return false;
        }

        LocalDate localDate = dateAjoutPr.getValue();
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une date valide !");
            return false;
        }

        if (ajoutSeance.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une séance !");
            return false;
        }

        return true;
    }

    private boolean verifierSaisiesEnregistrerEtat() {
        Personne etudiantSelectionne = listViewEtudiants.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = dateAjoutPr.getValue();
        Seance selectedSeance = ajoutSeance.getValue() != null ? Seance.valueOf(ajoutSeance.getValue()) : null;

        if (etudiantSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un étudiant !");
            return false;
        }

        if (selectedDate == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une date !");
            return false;
        }

        if (selectedSeance == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une séance !");
            return false;
        }

        return true;
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void classeSelectedEtatPresence(ActionEvent event) {
        String selectedClasseName = filtreClasse.getValue();
        if (selectedClasseName != null) {
            ServicePersonne servicePersonne = new ServicePersonne();
            // Retrieve students with their presence status for the selected class
            List<Personne> etudiantsAvecPresence = servicePersonne.getEtudiantsAvecPresencePourClasse(selectedClasseName);
            // Update the affichageEtatPresence ListView with the retrieved data
            affichageEtatPresence.getItems().clear();
            affichageEtatPresence.getItems().addAll(etudiantsAvecPresence);
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une classe !");
        }
    }

    @FXML
    void fillFormPresence(MouseEvent event) {
        Personne selectedPersonne = listViewEtudiants.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null) {
            nometudiant.setText(selectedPersonne.getNom());
        }
    }


    public void classeSelected(ActionEvent mouseEvent) {
        String selectedClasseName = nomDesClasses.getValue();

        if (selectedClasseName != null) {
            ServiceClasse serviceClasse = new ServiceClasse();
            classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);

            if (selectedClasse != null) {
                ServicePersonne servicePersonne = new ServicePersonne();
                List<Personne> etudiants = servicePersonne.getEtudiantsDeLaClasse(selectedClasse);
                listViewEtudiants.getItems().clear();
                listViewEtudiants.getItems().addAll(etudiants);
            }
        }
    }


}
