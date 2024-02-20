package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Niveau;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServicePlanDetude;

import java.sql.SQLException;

public class AjouterPlanDetude {

    @FXML
    private ComboBox<Niveau> cbniveau;

    @FXML
    private TextField tfnomprogramme;
     private final ServicePlanDetude sp=new ServicePlanDetude();
     @FXML
     public void initialize(){
         cbniveau.getItems().setAll(Niveau.values());

     }
    @FXML
    void ajouterPlanDetude(ActionEvent event) {
        // ps.add(new Personne(0,nomtf.getText(),prenomTF.getText(),Integer.parseInt(ageTF.getText())));

        /*PlanDetude p=new PlanDetude();
        p.setNomProgramme(tfnomprogramme.getText());
        p.setNiveau((tn.esprit.models.Niveau) cbniveau.getValue());
        p.setCreditsRequisTotal(0);
        p.setDureeTotal(0);*/
          try {
              sp.ajouter(new PlanDetude(tfnomprogramme.getText(), cbniveau.getValue(), 0, 0));
          }catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
          }

    }

}
