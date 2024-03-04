package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.esprit.models.Matiere;
import tn.esprit.esprit.services.ServiceMatiere;

public class AfficherMatiereEtudiant {
    @FXML
    private ListView<Matiere> listMatiere;
    ObservableList<Matiere> data= FXCollections.observableArrayList();
    ServiceMatiere sm=new ServiceMatiere();


    public void initialize() {

        refresh();
    }
    public void refresh(){
        data.clear();
        data= FXCollections.observableArrayList(sm.afficher());
        listMatiere.setItems(data);

    }

}

