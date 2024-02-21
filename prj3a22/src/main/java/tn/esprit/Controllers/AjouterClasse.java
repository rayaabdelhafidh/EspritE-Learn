package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.Models.classe;
import tn.esprit.Models.filiere;
import tn.esprit.Models.niveaux;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AjouterClasse implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField classeNameLabel;



    @FXML
    private ChoiceBox<String> lbFiliere;

    @FXML
    private TextField NbreEtudLabel;

    @FXML
    private ChoiceBox<String> lbNiveau;

    @FXML
    private Button AjouterClButton;





    @FXML
    private TableView<?> TableViewListEtud;



    @FXML
    private Button EnregistrerClButton;

@FXML
    void CreateClasse(ActionEvent event) throws SQLException, IOException {
        ServiceClasse SCl = new ServiceClasse();

            int nombreEtudiantsTexte = Integer.parseInt(NbreEtudLabel.getText());
            filiere selectedFiliere = filiere.valueOf(lbFiliere.getValue());
            niveaux selectedNiveau = niveaux.valueOf(lbNiveau.getValue());
            //int nombreEtudiants = Integer.parseInt(nombreEtudiantsTexte);
            classe cl2 = new classe(classeNameLabel.getText(), selectedFiliere, nombreEtudiantsTexte, selectedNiveau);
            SCl.add(cl2);
            JOptionPane.showMessageDialog(null, "Classe ajoutée avec succès ! ");

            // Rafraîchir les données de ChoiceBox en réinitialisant la méthode initialize
            initialize(null, null);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterClasse.fxml"));
            Parent root= loader.load();
            AjouterClButton.getScene().setRoot(root);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classe Cl1 = new classe();

        ObservableList<String> filieresList = FXCollections.observableArrayList();
        for (filiere f : filiere.values()) {
            filieresList.add(f.toString());
        }
        lbFiliere.setItems(filieresList);

        // Récupérer les valeurs de l'énumération niveaux et les ajouter à la ChoiceBox lbNiveau
        ObservableList<String> niveauxList = FXCollections.observableArrayList();
        for (niveaux f : niveaux.values()) {
            niveauxList.add(f.toString());
        }
        lbNiveau.setItems(niveauxList);

        ObservableList<classe> classees = FXCollections.observableArrayList();
        ServiceClasse SCL1=new ServiceClasse();
        List<classe> c=SCL1.getAll();
        for (classe cat : c)
        {
            classees.add(cat);
        }
       // listClasseCombo.setItems().set;

    }

    private boolean verifs() {
        System.out.println(classeNameLabel.getText());
    if (classeNameLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "le champ nom est obligatoire");
            return false;
        }

        if (NbreEtudLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "le champ Nombre des étudiants est obligatoire");
            return false;
        }
        return true;
    }

    public void TabViewEtudList(ActionEvent event) {
    }


}
