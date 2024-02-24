package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import tn.esprit.models.Matiere;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServicePlanDetude;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
//import java.io.IOException;
//import java.sql.SQLException;

public class AfficherPlanDetude {

    @FXML
    private ListView<PlanDetude> listviewplandetude;
    ServicePlanDetude sp = new ServicePlanDetude();

    public void initialize() {

       /*Set<PlanDetude> items = sp.afficher();
        ObservableList<PlanDetude> observableList = FXCollections.observableArrayList(items);
        listviewplandetude.setItems(observableList);*/

        try{


            Set<PlanDetude> items = sp.afficher();
            ObservableList<PlanDetude> observableList = FXCollections.observableArrayList(items);
            listviewplandetude.setItems(observableList);
        }
        catch(Exception e){
            System.out.println("erreur"+e.getMessage());
        }

    }




}