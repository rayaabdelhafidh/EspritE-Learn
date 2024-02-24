package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Matiere;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServiceMatiere;
import tn.esprit.service.ServicePlanDetude;
import javafx.scene.input.MouseEvent;

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
    @FXML
    private ListView<Matiere> listMatiere;
    ObservableList<Matiere> data= FXCollections.observableArrayList();

    public void initialize() {
        cbsemester.getItems().setAll(1,2);
        cbenseignant.getItems().setAll("Ahmed","Ali","Sarra");
        cbplandetude.getItems().setAll(sp.getNomPlanDetude());
        refresh();
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
        refresh();

    }

    @FXML
    void fillforum(MouseEvent event) {
        Matiere m=listMatiere.getSelectionModel().getSelectedItem();
        if(m!=null){
            tfcoeff.setText(String.valueOf(m.getCoefficient()));
            tfcredit.setText(String.valueOf(m.getCredit()));
            tfnbrheures.setText(String.valueOf(m.getNbrHeure()));
            tfnommatiere.setText(m.getNomM());
            cbsemester.setValue(m.getSemester());
            /*int idp=m.getIdPlanDetude();
            PlanDetude p=sp.getById(idp);
            cbplandetude.setValue(p.getNomProgramme());*/
            String nom=sp.getById(m.getIdPlanDetude()).getNomProgramme();
            cbplandetude.setValue(nom);
            /*String nom2=su.getById(m.getIdEnseignant()).getUsername();
            cbplandetude.setValue(nom2);*/
        }
    }
    @FXML
    void modifierMatiere(ActionEvent event) {
        Matiere m=listMatiere.getSelectionModel().getSelectedItem();
        int nbHeure=m.getNbrHeure();
        int credit=m.getCredit();
        if(m!=null){
            m.setCoefficient(Integer.valueOf(tfcoeff.getText()));
            m.setNbrHeure(Integer.valueOf(tfnbrheures.getText()));
            m.setCredit(Integer.valueOf(tfcredit.getText()));
            m.setNomM(tfnommatiere.getText());
            m.setIdEnseignant(1);
            m.setSemester(cbsemester.getValue());
            int idPlanDetude=sp.getIdByNomProgramme(cbplandetude.getValue());
            m.setIdPlanDetude(idPlanDetude);
            PlanDetude planDetude=sp.getById(idPlanDetude);
            planDetude.setDureeTotal(planDetude.getDureeTotal()+(m.getNbrHeure()-nbHeure));
            planDetude.setCreditsRequisTotal(planDetude.getCreditsRequisTotal()+(m.getCredit()-credit));
            sp.modifier(planDetude);
            sm.modifier(m);
            refresh();
        }
    }

    @FXML
    void supprimerMatiere(ActionEvent event) {
        Matiere m=listMatiere.getSelectionModel().getSelectedItem();
        if(m!=null){
            sm.supprimer(m.getIdM());
            refresh();
        }
    }
   public void refresh(){
       data.clear();
       data=FXCollections.observableArrayList(sm.afficher());
       listMatiere.setItems(data);

   }

}
