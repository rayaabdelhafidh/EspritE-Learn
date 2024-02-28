package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import tn.esprit.Services.ServiceMatiere;
import tn.esprit.controllers.AjouterEmploi;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FormulaireEmploi implements Initializable {

    @FXML
    private Button buttonCreer;

    @FXML
    private ComboBox<Integer> classeVals;

    @FXML
    private DatePicker dernierDate;

    @FXML
    private ComboBox<String> filiereVals;

    @FXML
    private CheckListView<String> matieres;

    @FXML
    private ComboBox<String> niveauVals;

    @FXML
    private ComboBox<String> optionVals;

    @FXML
    private DatePicker premierDate;

    private AjouterEmploi matieresController;
    private ObservableList<String> selectedMatieres;

    public void setAjouterEmploiController(AjouterEmploi matieresController) {
        this.matieresController = matieresController;
    }

    @FXML
    private void handleButtonCreerClicked() {
        if (validateFields()) {
            System.out.println("Button Creer clicked");
            selectedMatieres = matieres.getCheckModel().getCheckedItems();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEmploi.fxml"));
                Parent root = loader.load();
                AjouterEmploi ajouterEmploiController = loader.getController();
                ajouterEmploiController.setMatieresList(selectedMatieres);
                ajouterEmploiController.setWeekDates(premierDate.getValue(), dernierDate.getValue());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs, s'il vous plaît.");
            alert.showAndWait();
        }
    }

    private boolean validateFields() {
        return classeVals.getValue() != null &&
                premierDate.getValue() != null &&
                dernierDate.getValue() != null &&
                filiereVals.getValue() != null &&
                niveauVals.getValue() != null &&
                optionVals.getValue() != null &&
                !matieres.getCheckModel().getCheckedItems().isEmpty();
    }

    private final ServiceMatiere sm = new ServiceMatiere();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        niveauVals.getItems().addAll("1ère année", "2ème année", "3ème année", "4ème année", "5ème année");
        filiereVals.getItems().addAll("Tranc Commune", "IT", "TC", "Génie Civil", "GE");
        optionVals.getItems().addAll("Pas Spécifié", "DS", "Informatique Financière Et Ingénierie", "GL", "ERP-BI",
                "SIM", "TWIN", "SLEAM", "NIDS", "ArcTIC", "WIN", "IoSyS", "Oil And Gas Engineering",
                "UE Optionnelle Structures Et Ouvrages", "UE Optionnelle Eco-Construction & Efficacité Energétique",
                "UE Optionnelle Ponts Et Chaussées", "OGI", "Mécatronique");
        classeVals.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        List<String> nomMatieres = sm.getNomMatiere();
        matieres.getItems().addAll(nomMatieres);
    }
}
