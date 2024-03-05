package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.esprit.models.PlanDetude;
import tn.esprit.esprit.services.ServicePlanDetude;

import java.io.IOException;

public class AfficherPlanDetudeEtudiant {
    @FXML
    private ListView<PlanDetude> listPlanDetude;
    @FXML
    private ImageView gotoetud;
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
