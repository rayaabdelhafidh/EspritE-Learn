package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.Models.classe;
import tn.esprit.Models.filiere;
import tn.esprit.Models.niveaux;
import tn.esprit.utilse.MainFX;

//import javafx.scene.input.MouseEvent;
//import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.lang.String.*;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class GestClasse_VueAdmin implements Initializable {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private GridPane grid;

    @FXML
    private TableColumn<classe, filiere> filiereListSearch;


    @FXML
    private TableColumn<classe, String> classNameListSearch;

    @FXML
    private TableColumn<classe, niveaux> ListSearchNiv;

    @FXML
    private ChoiceBox<String> filtreParFiliere;

    @FXML
    private ChoiceBox<String> filtreParNiveau;



    @FXML
    private TableColumn<classe, String> NbEtudiSearchList;
    @FXML
    private TableView<classe> searchListeDesClasses;

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
    private TextField labelNomModif;

    @FXML
    private ChoiceBox<String> lbFiliereModif;

    @FXML
    private TextField NbreEtudeLabel;


    @FXML
    private ChoiceBox<String> lbNiveauModif;

    @FXML
    private TextField filterField;





//    ObservableList<classe> data= FXCollections.observableArrayList();
//    ObservableList<classe> data2= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//**************************ajouter/update/supp*****************************

        ObservableList<String> filieresList = FXCollections.observableArrayList();
        for (filiere f : filiere.values()) {
            filieresList.add(f.toString());
        }
        lbFiliereModif.setItems(filieresList);
        lbFiliere.setItems(filieresList);
        filtreParFiliere.setItems(filieresList);
        // Récupérer les valeurs de l'énumération niveaux et les ajouter à la ChoiceBox lbNiveau
        ObservableList<String> niveauxList = FXCollections.observableArrayList();
        for (niveaux f : niveaux.values()) {
            niveauxList.add(f.toString());
        }
        lbNiveauModif.setItems(niveauxList);
        lbNiveau.setItems(niveauxList);
        filtreParNiveau.setItems(niveauxList);
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
        //initializeSearchFilter();
        loadDateSearch();
        refresh1();
        search();
//***********initialisation des filtres**********************

        ///***********filtre**********
        filtreParFiliere.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            refresh1();
        });

        filtreParNiveau.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            refresh1();
        });
    }
    @FXML
    void CreateClasse(ActionEvent event) {
        // Vérification des champs
        if (!verifCreateClasse()) {
            return;
        }

        ServiceClasse SCl = new ServiceClasse();
        String nomClasse = classeNameLabel.getText();
        int nombreEtudiantsTexte = Integer.parseInt(NbreEtudLabel.getText());
        filiere selectedFiliere = filiere.valueOf(valueOf(lbFiliere.getValue()));
        niveaux selectedNiveau = niveaux.valueOf(valueOf(lbNiveau.getValue()));
    if (!isNomCompatibleAvecNiveau(nomClasse, selectedNiveau)) {
           showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la classe n'est pas compatible avec le niveau sélectionné !");
         return;
      }
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
        // String idCl = idDonnePourModif.getText();
        int nombreEtudiants = Integer.parseInt(NbreEtudeLabel.getText());
        filiere selectedFiliere = filiere.valueOf(lbFiliereModif.getValue());
        niveaux selectedNiveau = niveaux.valueOf(lbNiveauModif.getValue());
        if (!isNomCompatibleAvecNiveau(nomClassModif, selectedNiveau)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom de la classe n'est pas compatible avec le niveau sélectionné !");
            return;
        }
        // Création d'un objet classe avec les valeurs saisies
        classe cl = new classe();
        // cl.setidC(Integer.parseInt(idCl));
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




    private void search(){
        ServiceClasse s = new ServiceClasse();
        ObservableList<classe> listClasse = FXCollections.observableArrayList(s.getAll());
        FilteredList<classe> filteredData = new FilteredList<>(listClasse,p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(classe ->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if(String.valueOf(classe.getNomClasse()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (classe.getFiliere() != null && classe.getFiliere().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (classe.getNiveaux() != null && classe.getNiveaux().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (String.valueOf(classe.getNbreEtud()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<classe> sortedData = new SortedList<>(filteredData);

        searchListeDesClasses.setItems(sortedData);
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
        //idsuppListe.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        nbEtudiantC1.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classeNameC1.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereC1.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        niveauC1.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        tableClasse1.setItems(FXCollections.observableArrayList(classes));
    }    private void loadDateSearch() {
        ServiceClasse serviceClassesearch = new ServiceClasse();
        List<classe> classes = serviceClassesearch.getAll();
        //idListSearch.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        NbEtudiSearchList.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classNameListSearch.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereListSearch.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        ListSearchNiv.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
        // Ajout des données à la table 1
        searchListeDesClasses.setItems(FXCollections.observableArrayList(classes));
    }

    private void loadDate2() {
        ServiceClasse serviceClasse = new ServiceClasse();
        List<classe> classes = serviceClasse.getAll();
        // IddansListe.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
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
            // idDonnePourModif.setText(String.valueOf(selectedClasse.getidC()));
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
            // idClasseSupp.setText(String.valueOf(selectedClasse.getidC()));
            nomclasseSupp.setText(selectedClasse.getNomClasse());

        }
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







    public void refresh1(){           //******************CardView*****************************************************
        ServiceClasse sc=new ServiceClasse();

        grid.getChildren().clear();
        ArrayList<classe> list=sc.getAll();
        int column=0;
        int row=1;
        for(classe c:list){
            try {

                FXMLLoader card=new FXMLLoader(MainFX.class.getResource("/classe-card-view.fxml"));
                AnchorPane anchorPane=card.load();
                ClassCardView item=card.getController();
                item.remplireData(c);
                if(column==2){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);
                GridPane.setMargin(anchorPane,new Insets(10));
            }
            catch (IOException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    private boolean isNomCompatibleAvecNiveau(String nomClassee, niveaux niveau) {
        // Vérifiez ici si le nom de la classe est compatible avec le niveau sélectionné
        switch (niveau) {
            case _1A:
                // Exemple : Si le niveau est _1A, le nom de la classe doit commencer par "1A"
                return nomClassee.startsWith("1A");

            case _2A:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _2A
                return nomClassee.startsWith("2A");

            case _2P:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _2P
                return nomClassee.startsWith("2P");

            case _3A:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _3A
                return nomClassee.startsWith("3A");

            case _3B:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _3B
                return nomClassee.startsWith("3B");

            case _4A:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _4A
                return nomClassee.startsWith("4A");

            case _5A:
                // Ajoutez d'autres conditions selon votre logique métier pour le niveau _5A
                return nomClassee.startsWith("5A");

            default:
                // Autre logique par défaut si nécessaire
                return false;
        }
    }


}





