package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Matiere;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServiceMatiere;
import tn.esprit.service.ServicePlanDetude;

public class AjouterMatiere {

    @FXML
    private ComboBox<String> cbenseignant;

    @FXML
    private ComboBox<String > cbplandetude;

    @FXML
    private ComboBox<Integer> cbsemester;

    @FXML
    private TextField tfcoeff;

    @FXML
    private TextField tfcredit;

    @FXML
    private TextField tfnbrheures;

    @FXML
    private TextField tfnommatiere;
   ServiceMatiere sm=new ServiceMatiere();
    ServicePlanDetude sp=new ServicePlanDetude();

    public void initialize() {
        cbsemester.getItems().setAll(1,2);
        cbenseignant.getItems().setAll("Ahmed","Ali","Sarra");
        cbplandetude.getItems().setAll(sp.getNomPlanDetude());
    }

    @FXML
    void ajouterMatiere(ActionEvent event) {
     // sm.ajouter(new Matiere(tfnommatiere.getText(),1,tfnbrheures.getText(),tfcoeff.getText(),cbsemester.getValue(),tfcredit.getText(),cbplandetude.getValue()));

        Matiere m=new Matiere();
        m.setCoefficient(Integer.valueOf(tfcoeff.getText()));
        m.setNbrHeure(Integer.valueOf(tfnbrheures.getText()));
        m.setCredit(Integer.valueOf(tfcredit.getText()));
        m.setNomM(tfnommatiere.getText());
        m.setIdEnseignant(1);
        m.setSemester(cbsemester.getValue());
        int idPlanDetude=sp.getIdByNomProgramme(cbplandetude.getValue());
        m.setIdPlanDetude(idPlanDetude);
        PlanDetude planDetude=sp.getById(idPlanDetude);
        planDetude.setDureeTotal(planDetude.getDureeTotal()+m.getNbrHeure());
        planDetude.setCreditsRequisTotal(planDetude.getCreditsRequisTotal()+m.getCredit());
        sp.modifier(planDetude);
        sm.ajouter(m);


    }


}
