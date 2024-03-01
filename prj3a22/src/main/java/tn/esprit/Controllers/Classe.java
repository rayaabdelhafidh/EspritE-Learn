package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private FilteredList<classe> filteredList;

    @FXML
    private TableColumn<classe, filiere> filiereListSearch;
    @FXML
    private TableColumn<classe, Integer> idListSearch;

    @FXML
    private TableColumn<classe, String> classNameListSearch;

    @FXML
    private TableColumn<classe, niveaux> ListSearchNiv;

    @FXML
    private TextField searchFieldFilier;



    @FXML
    private TableColumn<classe, String> NbEtudiSearchList;
    @FXML
    private TableView<classe> searchListeDesClasses;
    @FXML
    private TextField idClasseSupp;
    public TableView<classe> tableClasse2;
    @FXML
    private TableColumn<classe, String> classeNameC2;
    @FXML
    private TableColumn<classe, Integer> IddansListe;
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
    private ListView<classe> afficherListeDesClasse;


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
    private TableColumn<classe, Integer> idsuppListe;

    @FXML
    private TextField idDonnePourModif;

    @FXML
    private TextField labelNomModif;

    @FXML
    private ChoiceBox<String> lbFiliereModif;

    @FXML
    private TextField NbreEtudeLabel;
    @FXML
    private TextField searchField;

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

    private void initializeSearchFilter() {
        // Créez une FilteredList à partir de votre ObservableList de classes
        filteredList = new FilteredList<>(data2, p -> true);

        // Ajoutez un écouteur de changement de texte au champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(classe -> {
                // Si la recherche est vide, afficher toutes les classes
                if ((newValue == null) || newValue.isEmpty()) {
                    return true;
                }

                // Convertissez la recherche en minuscules pour une recherche insensible à la casse
                String lowerCaseFilter = newValue.toLowerCase();

                // Vérifiez si le nom de la classe contient la chaîne de recherche
                if (classe.getNomClasse().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Correspondance trouvée
                }

                // Aucune correspondance trouvée
                return false;
            });
        });

        // Enveloppez la FilteredList dans une SortedList
        SortedList<classe> sortedList = new SortedList<>(filteredList);

        // Liez la SortedList à votre TableView
        searchListeDesClasses.setItems(sortedList);

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

        ServiceClasse SCAff=new ServiceClasse();
        ObservableList<classe> listeDesClasses = FXCollections.observableArrayList(SCAff.getAll());
        // Afficher la liste dans la ListView
        afficherListeDesClasse.setItems(listeDesClasses);
        //****************************recherche****************
        initializeSearchFilter();
        loadDateSearch();






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
        idsuppListe.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        nbEtudiantC1.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classeNameC1.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereC1.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        niveauC1.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        tableClasse1.setItems(FXCollections.observableArrayList(classes));
    }    private void loadDateSearch() {
        ServiceClasse serviceClassesearch = new ServiceClasse();
        List<classe> classes = serviceClassesearch.getAll();
        idListSearch.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        NbEtudiSearchList.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classNameListSearch.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereListSearch.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        filiereListSearch.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        searchListeDesClasses.setItems(FXCollections.observableArrayList(classes));
    }

    private void loadDate2() {
        ServiceClasse serviceClasse = new ServiceClasse();
        List<classe> classes = serviceClasse.getAll();
        IddansListe.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
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
        loadDateSearch();
    }

    @FXML
    public void fillForumm(MouseEvent mouseEvent) {
        classe selectedClasse = tableClasse2.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            idDonnePourModif.setText(String.valueOf(selectedClasse.getidC()));
            labelNomModif.setText(selectedClasse.getNomClasse());
            lbFiliereModif.getSelectionModel();
            NbreEtudeLabel.setText(String.valueOf(selectedClasse.getNbreEtud()));
            lbNiveauModif.getSelectionModel().getSelectedItem();
        }
    }
@FXML
    public void fillForum(MouseEvent mouseEvent) {
        classe selectedClasse = tableClasse1.getSelectionModel().getSelectedItem();
        if (selectedClasse != null) {
            idClasseSupp.setText(String.valueOf(selectedClasse.getidC()));
            nomclasseSupp.setText(selectedClasse.getNomClasse());

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

    @FXML
    void searchByNom(ActionEvent event) {
        String searchTerm = searchField.getText().trim(); // Récupérer le texte saisi dans le champ de recherche

        if (searchTerm.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de recherche", "Veuillez saisir un terme de recherche !");
            return;
        }

        // Appeler la méthode de service pour rechercher la classe par nom
        ServiceClasse serviceClasse = new ServiceClasse();
        classe foundClasse = serviceClasse.getClasseByNom(searchTerm);

        if (foundClasse != null) {
            // Créer une nouvelle liste observable contenant uniquement la classe trouvée
            ObservableList<classe> foundClasseList = FXCollections.observableArrayList();
            foundClasseList.add(foundClasse);

            // Mettre à jour la liste filtrée pour afficher uniquement la classe trouvée
            filteredList.setPredicate(classe -> classe.getidC() == foundClasse.getidC());

            // Rafraîchir la liste des classes affichées dans la TableView
            searchListeDesClasses.setItems(foundClasseList);
        } else {
            // Afficher un message d'erreur si aucune classe n'est trouvée
            showAlert(Alert.AlertType.ERROR, "Erreur de recherche", "Aucune classe trouvée avec le nom : " + searchTerm);
        }
        searchField.clear();
    }




    @FXML
    void SearchByID(ActionEvent event) {
        String searchTerm = searchFieldFilier.getText().trim(); // Récupérer le texte saisi dans le champ de recherche

        if (searchTerm.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de recherche", "Veuillez saisir un terme de recherche !");
            return;
        }

        // Appeler la méthode de service pour rechercher la classe par nom
        ServiceClasse serviceClasse = new ServiceClasse();
        classe foundClasse = serviceClasse.getClasse(Integer.parseInt(searchTerm));

        if (foundClasse != null) {
            // Créer une nouvelle liste observable contenant uniquement la classe trouvée
            ObservableList<classe> foundClasseList = FXCollections.observableArrayList();
            foundClasseList.add(foundClasse);

            // Mettre à jour la liste filtrée pour afficher uniquement la classe trouvée
            filteredList.setPredicate(classe -> classe.getidC() == foundClasse.getidC());

            // Rafraîchir la liste des classes affichées dans la TableView
            searchListeDesClasses.setItems(foundClasseList);
        } else {
            // Afficher un message d'erreur si aucune classe n'est trouvée
            showAlert(Alert.AlertType.ERROR, "Erreur de recherche", "Aucune classe trouvée avec l'id : " + searchTerm);
        }
        searchFieldFilier.clear();
    }


}





