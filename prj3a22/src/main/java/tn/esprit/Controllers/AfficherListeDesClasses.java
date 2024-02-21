package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.Models.classe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherListeDesClasses implements Initializable {

    @FXML
    private TableView<classe> tableClasse;

    @FXML
    private TableColumn<classe, String> classeNameC;

    @FXML
    private TableColumn<classe, String> filiereC;

    @FXML
    private TableColumn<classe, Integer> nbEtudiantC;

    @FXML
    private TableColumn<classe, String> niveauC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        loadDate();


        // Ajoute les classes à la ListView
       // ListeDesClasseListView.getItems().addAll(classes);
    }
    @FXML
    void GetAllClass(ActionEvent event) {
        try {
            // Charger la vue ListeDesClasses.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeDesClasses.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur AfficherListeDesClasses
            AfficherListeDesClasses controller = loader.getController();

            // Créer une nouvelle fenêtre
            Stage stage = new Stage();

            // Définir la scène avec la vue chargée
            stage.setScene(new Scene(root));

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    ObservableList<classe> ActList = FXCollections.observableArrayList();
    ServiceClasse uc = new ServiceClasse();

    private void refreshTable() {
        ActList.addAll(uc.getAll());

        tableClasse.setItems(ActList);
    }
    private void loadDate() {
        nbEtudiantC.setCellValueFactory(new PropertyValueFactory<>("nbreEtud"));
        classeNameC.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        filiereC.setCellValueFactory(new PropertyValueFactory<>("filiere"));
        niveauC.setCellValueFactory(new PropertyValueFactory<>("niveaux"));
//        CAction.setCellValueFactory(new Button<>("Supprimer"));

        refreshTable();

    }



}
