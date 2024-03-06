package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.esprit.models.PlanDetude;
import tn.esprit.esprit.services.ServicePlanDetude;

public class AfficherPlanDetudeEtudiant {
    @FXML
    private ListView<PlanDetude> listPlanDetude;
    ServicePlanDetude sp=new ServicePlanDetude();

    ObservableList<PlanDetude> data= FXCollections.observableArrayList();
    @FXML
    public void initialize(){

        refresh();
    }

    public void refresh(){
        data.clear();
        data= FXCollections.observableArrayList(sp.afficher());
        listPlanDetude.setItems(data);
    }

}
