package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.esprit.models.Cour;
import tn.esprit.esprit.models.Matiere;
import tn.esprit.esprit.services.ServiceMatiere;

import java.io.IOException;
import java.util.Set;

public class AfficherMatiereEtudiant {
    @FXML
    private ComboBox<String> cbtri;
    @FXML
    private ListView<Matiere> listMatiere;
    @FXML
    private ImageView gotoetud;
    ObservableList<Matiere> data= FXCollections.observableArrayList();
    ServiceMatiere sm=new ServiceMatiere();


    public void initialize() {
        cbtri.getItems().setAll("Coefficient","Nom");
        refresh(sm.afficher());
    }
    public void refresh(Set<Matiere> matieres){
        data.clear();
        data= FXCollections.observableArrayList(matieres);
        listMatiere.setItems(data);

    }
    @FXML
    void tri(ActionEvent event) {
        refresh(sm.sortByCritere(cbtri.getValue()));
    }

    @FXML
    void gotoetudiant(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEtudiant.fxml"));
            Parent root = loader.load();
            gotoetud.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

