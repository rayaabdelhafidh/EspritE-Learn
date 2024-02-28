package tn.esprit.Controllers;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.IServices.ServicePersonne;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PresenceController implements Initializable {

    @FXML
    private ListView<Personne> listViewEtudiants;

    @FXML
    private TableColumn<Personne, Integer> idEtudiant;

    @FXML
    private TableColumn<Personne, String > nomEtudi;

    @FXML
    private TableColumn<Personne, String> PrenomEtudi;

    @FXML
    private TableColumn<Personne, String> etatPresence;

    @FXML
    private TextField nometudiant;


    @FXML
    private DatePicker dateAjoutPr;

    @FXML
    private ChoiceBox<String> ajoutSeance;

    @FXML
    private ChoiceBox<String> nomDesClasses;

    @FXML
    private ListView<Presence> listViewAjout;

    private ServicePresence servicePresence;

    public PresenceController() {
        this.servicePresence = new ServicePresence();
    }

    @FXML
    private CheckBox checkBoxAbsent;

    @FXML
    private CheckBox checkBoxPresent;

    @FXML
    void handleAbsentCheckbox(ActionEvent event) {
        if (checkBoxAbsent.isSelected()) {
            checkBoxPresent.setSelected(false); // Désélectionner l'autre CheckBox si celle-ci est sélectionnée
        }
    }

    @FXML
    void handlePresentCheckbox(ActionEvent event) {
        if (checkBoxPresent.isSelected()) {
            checkBoxAbsent.setSelected(false); // Désélectionner l'autre CheckBox si celle-ci est sélectionnée
        }
    }

    @FXML
    public void CreatePresence(ActionEvent event) {
        if (!verifierSaisies()) {
            return;
        }

        String selectedClasseName = nomDesClasses.getValue(); // Récupérer le nom de la classe sélectionnée
        ServiceClasse serviceClasse = new ServiceClasse();
        classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);

        if (selectedClasse == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Classe non trouvée !");
            return;
        }

        LocalDate localDate = dateAjoutPr.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Seance seance = Seance.valueOf(ajoutSeance.getValue());
        String etatPresence = checkBoxAbsent.isSelected() ? "Absent" : "Présent";

        System.out.println("Présence enregistrée pour l'étudiant " + nometudiant + " : " + etatPresence);
        Presence presence = new Presence(date, seance, selectedClasse.getNomClasse());

        try {
            servicePresence.addPresence(presence);
            refreshPresenceList();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la présence : " + e.getMessage());
        }
        showAlert(Alert.AlertType.CONFIRMATION,"confirmation","La presence est effectuée avec succés ! ");
    }

    private boolean verifierSaisies() {
        String selectedClasseName = nomDesClasses.getValue(); // Récupérer le nom de la classe sélectionnée

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

        if (!checkBoxAbsent.isSelected() && !checkBoxPresent.isSelected()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner l'état de présence de l'étudiant !");
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




    private void refreshPresenceList() {
        ArrayList<Presence> presences = servicePresence.getAll();
       // listViewAjout.getItems().clear();
        loadDate();
        listViewAjout.getItems().addAll(presences);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPresenceList();
        ObservableList<String> presenceList = FXCollections.observableArrayList();
        for (Seance s : Seance.values()) {
            presenceList.add(s.toString());
        }
        ajoutSeance.setItems(presenceList);

        ServiceClasse scl = new ServiceClasse();

        // Charger les noms des classes depuis la base de données
        ArrayList<String> classes = scl.getNomClasses();

        // Créer une liste observable pour les noms des classes
        ObservableList<String> classeList = FXCollections.observableArrayList(classes);

        // Définir les noms des classes dans le ChoiceBox
        nomDesClasses.setItems(classeList);
        refreshPresenceList();




    }
    private void loadDate() {
        ServicePresence servicePresence = new ServicePresence();
        List<Presence> presenceees = servicePresence.getAll();
//        nbEtudiantC1.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
//        classeNameC1.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
//        filiereC1.setCellValueFactory(new PropertyValueFactory<>("filiere"));
//        niveauC1.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        listViewAjout.setItems(FXCollections.observableArrayList(presenceees));
    }

    public void classeSelected(ActionEvent mouseEvent) {

        String selectedClasseName = nomDesClasses.getValue();

        if (selectedClasseName != null) {
            // Appel du service pour obtenir les étudiants de la classe sélectionnée
            ServiceClasse serviceClasse = new ServiceClasse();
            classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);

            if (selectedClasse != null) {
                // Appel du service pour obtenir les étudiants de la classe sélectionnée
                ServicePersonne servicePersonne = new ServicePersonne();
                List<Personne> etudiants = servicePersonne.getEtudiantsDeLaClasse(selectedClasse);

                // Affichage des étudiants dans le ListView
                listViewEtudiants.getItems().clear();
                listViewEtudiants.getItems().addAll(etudiants);
            }
        }


    }

    @FXML
    void fillFormPresence(MouseEvent event) {
        Personne selectedPersonne = listViewEtudiants.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null) {
            nometudiant.setText(String.valueOf(selectedPersonne.getNom()));


        }}



    @FXML
    void EnregistrerEtatPresence(ActionEvent event) {
        ServicePersonne SP1 = new ServicePersonne();

        for (Personne etudiant : listViewEtudiants.getItems()) {
            // Vérifier l'état de présence de l'étudiant
            if (SP1.isAbsent(etudiant)) {
                // Enregistrer l'étudiant comme absent dans la base de données
                SP1.enregistrerEtatPresence(etudiant.getIdP(), EtatPresence.Absent);
            } else if (SP1.isPresent(etudiant)) {
                // Enregistrer l'étudiant comme présent dans la base de données
                SP1.enregistrerEtatPresence(etudiant.getIdP(), EtatPresence.Present);
            }

        }

    }



}
