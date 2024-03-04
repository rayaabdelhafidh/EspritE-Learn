package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import tn.esprit.esprit.models.Cour;
import tn.esprit.esprit.models.Matiere;
import tn.esprit.esprit.services.ServiceMatiere;

import java.util.Set;

public class AfficherMatiereEtudiant {
    @FXML
    private ComboBox<String> cbtri;
    @FXML
    private ListView<Matiere> listMatiere;
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
}

