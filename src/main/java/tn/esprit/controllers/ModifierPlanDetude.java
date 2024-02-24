package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Niveau;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServicePlanDetude;
//import tn.esprit.models.Niveau;

public class ModifierPlanDetude {

    @FXML
    private ComboBox<Niveau> cbniveau1;

    @FXML
    private TextField tfnomprogramme1;
    private final ServicePlanDetude sp=new ServicePlanDetude();
    PlanDetude p;

    public void initialize(){
        cbniveau1.getItems().setAll(Niveau.values());

    }


   /* @FXML
    public void setPlanDetude(PlanDetude planDetude) {
        this.planDetude = planDetude;
        // Mettez à jour les composants d'interface utilisateur avec les données du plan d'étude
        // Assurez-vous que vous avez des champs de texte ou des composants similaires dans votre interface utilisateur
        tfNomProgramme.setText(planDetude.getNomProgramme());
        cbNiveau.setValue(planDetude.getNiveau());
        // Set other fields as needed
    }*/

    @FXML
    void ModifierPlanDetude(ActionEvent event) {
       /* PlanDetude p=new PlanDetude(tfnomprogramme1.getText(),cbniveau1.getValue(),0,0);
        sp.modifier(p);*/
        int idPlanDetude=sp.getIdByNomProgramme(tfnomprogramme1.getText());
        p.setId(idPlanDetude);
        p.setNomProgramme(tfnomprogramme1.getText());
        p.setNiveau(cbniveau1.getValue());
        p.setDureeTotal(0);
        p.setCreditsRequisTotal(0);
        sp.modifier(p);


    }



}
