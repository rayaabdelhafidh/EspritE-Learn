package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.Models.classe;
import tn.esprit.Models.filiere;
import tn.esprit.Models.niveaux;

//import javafx.scene.input.MouseEvent;
//import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.*;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class Classe implements Initializable {
    @FXML
    private TextField idClasseSupp;
    public TableView<classe> tableClasse2;
    @FXML
    private TableColumn<classe, String> classeNameC2;

    @FXML
    private TableColumn<classe, String> filiereC2;

    @FXML
    private TableColumn<classe, Integer> nbEtudiantC2;

    @FXML
    private TableColumn<classe, String> niveauC2;

    @FXML
    private TableView<classe> tableClasse1;

    @FXML
    private TableColumn<classe, String> classeNameC1;

    @FXML
    private TableColumn<classe, String> filiereC1;

    @FXML
    private TableColumn<classe, Integer> nbEtudiantC1;

    @FXML
    private TableColumn<classe, String> niveauC1;

    @FXML
    private TextField classeNameLabel;

    @FXML
    private ChoiceBox<String> lbFiliere;

    @FXML
    private TextField NbreEtudLabel;

    @FXML
    private ChoiceBox<String> lbNiveau;

    @FXML
    private TextField nomclasseSupp;

    @FXML
    private TextField idDonnePourModif;

    @FXML
    private TextField labelNomModif;

    @FXML
    private ChoiceBox<String> lbFiliereModif;

    @FXML
    private TextField NbreEtudeLabel;

    @FXML
    private ChoiceBox<String> lbNiveauModif;






    ObservableList<classe> data= FXCollections.observableArrayList();
    ObservableList<classe> data2= FXCollections.observableArrayList();


    @FXML
    void CreateClasse(ActionEvent event) {
        // Vérification des champs
        if (!verifCreateClasse()) {
            return;
        }

        ServiceClasse SCl = new ServiceClasse();
        int nombreEtudiantsTexte = Integer.parseInt(NbreEtudLabel.getText());
        filiere selectedFiliere = filiere.valueOf(valueOf(lbFiliere.getValue()));
        niveaux selectedNiveau = niveaux.valueOf(valueOf(lbNiveau.getValue()));

        classe cl2 = new classe(classeNameLabel.getText(), selectedFiliere, nombreEtudiantsTexte, selectedNiveau);

        // Ajout de la classe
        SCl.add(cl2);

        // Affichage d'un message de confirmation
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Classe ajoutée avec succès !");

        // Rafraîchir les données de ChoiceBox en réinitialisant la méthode initialize
        initialize(null, null);
    }


    @FXML
    void updateClasse(ActionEvent event) {

        // Vérification des champs
        if (!verifUpdateClasse()) {
            return;
        }

        // Récupération des valeurs saisies dans l'interface utilisateur
        String nomClassModif = labelNomModif.getText();
        String idCl = idDonnePourModif.getText();
        int nombreEtudiants = Integer.parseInt(idDonnePourModif.getText());
        filiere selectedFiliere = filiere.valueOf(lbFiliereModif.getValue());
        niveaux selectedNiveau = niveaux.valueOf(lbNiveauModif.getValue());

        // Création d'un objet classe avec les valeurs saisies
        classe cl = new classe();
        cl.setidC(Integer.parseInt(idCl));
        cl.setNomClasse(nomClassModif);
        cl.setNbreEtud(nombreEtudiants);
        cl.setFiliere(selectedFiliere);
        cl.setNiveaux(selectedNiveau);

        // Appel du service pour mettre à jour la classe
        ServiceClasse svc = new ServiceClasse();
        svc.update(cl);

        // Affichage d'un message de succès
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Classe mise à jour avec succès !");
        initialize(null, null);
        loadDate2();

    }








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//**************************ajouter/update/supp*****************************

        ObservableList<String> filieresList = FXCollections.observableArrayList();
        for (filiere f : filiere.values()) {
            filieresList.add(f.toString());
        }
        lbFiliereModif.setItems(filieresList);
lbFiliere.setItems(filieresList);
        // Récupérer les valeurs de l'énumération niveaux et les ajouter à la ChoiceBox lbNiveau
        ObservableList<String> niveauxList = FXCollections.observableArrayList();
        for (niveaux f : niveaux.values()) {
            niveauxList.add(f.toString());
        }
        lbNiveauModif.setItems(niveauxList);
lbNiveau.setItems(niveauxList);
        ObservableList<classe> classees = FXCollections.observableArrayList();
        ServiceClasse SCL1=new ServiceClasse();
        List<classe> c=SCL1.getAll();
        classees.addAll(c);
        loadDate();
        loadDate2();


    }
    public void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private boolean verifCreateClasse() {
        if (classeNameLabel.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir le nom de la classe !");
            return false;
        }

        if (NbreEtudLabel.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir le nombre d'étudiants !");
            return false;
        }

        if (lbFiliere.getValue() == null || lbNiveau.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une filière !");
            return false;
        }

        if (lbNiveau.getValue() == null || lbNiveau.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner un niveau !");
            return false;
        }

        try {
            int nombreEtudiants = Integer.parseInt(NbreEtudLabel.getText());
            if (nombreEtudiants <= 0 || nombreEtudiants > 30) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étudiants doit être compris entre 1 et 30 !");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étudiants doit être un entier !");
            return false;
        }

        return true;
    }


    private boolean verifUpdateClasse() {
        if (labelNomModif.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir le nom de la classe !");
            return false;
        }

        if (NbreEtudeLabel.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir le nombre d'étudiants !");
            return false;
        }

        if (lbFiliereModif.getValue() == null || lbNiveauModif.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une filière !");
            return false;
        }

        if (lbNiveauModif.getValue() == null || lbNiveauModif.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner un niveau !");
            return false;
        }

        try {
            int nombreEtudiants = Integer.parseInt(NbreEtudeLabel.getText());
            if (nombreEtudiants <= 0 || nombreEtudiants > 30) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étudiants doit être compris entre 1 et 30 !");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le nombre d'étudiants doit être un entier !");
            return false;
        }

        return true;
    }


    private void loadDate() {
        ServiceClasse serviceClasse = new ServiceClasse();
        List<classe> classes = serviceClasse.getAll();
        nbEtudiantC1.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classeNameC1.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereC1.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        niveauC1.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        tableClasse1.setItems(FXCollections.observableArrayList(classes));
    }

    private void loadDate2() {
        ServiceClasse serviceClasse = new ServiceClasse();
        List<classe> classes = serviceClasse.getAll();
        nbEtudiantC2.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classeNameC2.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereC2.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        niveauC2.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 2
        tableClasse2.setItems(FXCollections.observableArrayList(classes));
    }

    public void refreshTable() {
        // Rafraîchissement des données de la table 1
        loadDate();

        // Rafraîchissement des données de la table 2
        loadDate2();
    }

    @FXML
    public void fillForumm(MouseEvent mouseEvent) {
        classe selectedClasse = tableClasse2.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            classeNameC2.setText(selectedClasse.getNomClasse());
            filiereC2.setText(String.valueOf(selectedClasse.getFiliere()));
            nbEtudiantC2.setText(String.valueOf(selectedClasse.getNbreEtud()));
            niveauC2.setText(String.valueOf(selectedClasse.getNiveaux()));
        }
    }

    public void fillForum(MouseEvent mouseEvent) {
        classe selectedClasse = tableClasse1.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            classeNameC1.setText(selectedClasse.getNomClasse());
            filiereC1.setText(String.valueOf(selectedClasse.getFiliere()));
            nbEtudiantC1.setText(String.valueOf(selectedClasse.getNbreEtud()));
            niveauC1.setText(String.valueOf(selectedClasse.getNiveaux()));
        }
    }


    private boolean verifyIdExists(int id) {
        // Appeler le service ou l'accès aux données pour vérifier l'existence de l'identifiant
        ServiceClasse serviceClasse = new ServiceClasse();
        classe existingClasse = serviceClasse.getClasse(id); // Supposons que getClasse(id) retourne la classe correspondante à l'ID

        // Vérifier si la classe existe déjà
        return existingClasse != null;
    }


    @FXML
    void DeleteClasse(ActionEvent event) throws SQLException {
        // Récupérer l'identifiant de la classe à supprimer à partir du champ texte idClasseSupp
        int id = Integer.parseInt(idClasseSupp.getText()); // Supposons que idClasseSupp est votre champ texte

        // Vérifier d'abord si l'identifiant existe
        if (!verifyIdExists(id)) {
            // Afficher un message d'erreur indiquant que l'identifiant n'existe pas
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "L'identifiant spécifié n'existe pas !");
            return; // Sortir de la méthode car l'identifiant n'existe pas
        }

        // Appeler le service ou l'accès aux données pour supprimer la classe
        ServiceClasse serviceClasse = new ServiceClasse();
        serviceClasse.delete(id); // Supposons que delete(int id) supprime la classe avec l'identifiant donné

        // Afficher un message de confirmation de suppression
        showAlert(Alert.AlertType.CONFIRMATION, "Suppression réussie", "La classe a été supprimée avec succès !");

        // Actualiser la table après la suppression
        refreshTable();
        idClasseSupp.clear();

    }
    private boolean verifyNomExists(String nomClasse) {
        // Appeler le service ou l'accès aux données pour vérifier si le nom de la classe existe
        ServiceClasse serviceClasse = new ServiceClasse();
        classe existingClasse = serviceClasse.getClasseByNom(nomClasse); // Supposons que getClasseByNom(nomClasse) retourne la classe correspondante au nom

        // Vérifier si la classe existe déjà
        return existingClasse != null;
    }

    @FXML
    public void DeleteByNom(ActionEvent event) {
        String nomClasse = nomclasseSupp.getText(); // Supposons que nomClasseToDelete soit le TextField où l'utilisateur entre le nom de la classe à supprimer

        // Vérifier d'abord si le nom de la classe existe
        if (!verifyNomExists(nomClasse)) {
            // Afficher un message d'erreur indiquant que le nom de la classe n'existe pas
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "Le nom de classe spécifié n'existe pas !");
            return; // Sortir de la méthode car le nom de la classe n'existe pas
        }

        // Appeler le service ou l'accès aux données pour supprimer la classe par son nom
        ServiceClasse serviceClasse = new ServiceClasse();
        serviceClasse.deleteByName(nomClasse);
        // Afficher un message de confirmation de suppression
        showAlert(Alert.AlertType.CONFIRMATION, "Suppression réussie", "La classe a été supprimée avec succès !");
        // Actualiser la table après la suppression
        refreshTable();
        // Effacer le TextField après la suppression
        nomclasseSupp.clear();
    }

}





