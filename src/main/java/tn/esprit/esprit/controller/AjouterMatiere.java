package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.esprit.models.Matiere;
import tn.esprit.esprit.models.PlanDetude;
import tn.esprit.esprit.services.ServiceMatiere;
import tn.esprit.esprit.services.ServicePlanDetude;
import tn.esprit.esprit.services.ServiceUser;

public class AjouterMatiere {

    @FXML
    private ComboBox<String> cbenseignant;

    @FXML
    private ComboBox<String> cbplandetude;

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
    ServiceUser su=new ServiceUser();
    @FXML
    private ListView<Matiere> listMatiere;
    ObservableList<Matiere> data= FXCollections.observableArrayList();

    public void initialize() {
        cbsemester.getItems().setAll(1,2);
        cbenseignant.getItems().setAll(su.getNomUser());
        cbplandetude.getItems().setAll(sp.getNomPlanDetude());
        refresh();
    }
    public String controleDeSaisire(){
        String erreur="";
        if(tfnommatiere.getText().isEmpty()){
            erreur+="-Nom matiere vide\n";
        }
        if(tfcredit.getText().isEmpty()){
            erreur+="-Champ credit vide\n";
        }
        if(tfcoeff.getText().isEmpty()){
            erreur+="-Champ Coefficient  vide\n";
        }

        if(cbplandetude.getValue()==null){
            erreur+="-Plan d'etude vide\n";
        }
        if(cbenseignant.getValue()==null){
            erreur+="-Champ enseignant vide\n";
        }
        if(cbsemester.getValue()==null){
            erreur+="-Champ semester vide\n";
        }
        if(tfnbrheures.getText().isEmpty() || !tfnbrheures.getText().matches("^(2[1-9]|3[0-9]|4[0-2])$")){

            erreur+="-Duree doit etre un nombre entre 21 et 42\n";
        }

        return erreur;
    }
    @FXML
    void ajouterMatiere(ActionEvent event) {
     // sm.ajouter(new Matiere(tfnommatiere.getText(),1,tfnbrheures.getText(),tfcoeff.getText(),cbsemester.getValue(),tfcredit.getText(),cbplandetude.getValue()));
        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
          else {
            Matiere m=new Matiere();
            m.setCoefficient(Integer.valueOf(tfcoeff.getText()));
            m.setNbrHeure(Integer.valueOf(tfnbrheures.getText()));
            m.setCredit(Integer.valueOf(tfcredit.getText()));
            m.setNomM(tfnommatiere.getText());
            /*
            int idPlanDetude=sp.getIdByNomProgramme(cbplandetude.getValue());
            m.setIdPlanDetude(idPlanDetude);
             */
            int idu=su.getIdByNomUser(cbenseignant.getValue());
            m.setIdEnseignant(idu);
           // m.setIdEnseignant(1);
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
           String nomu=su.getById(m.getIdEnseignant()).getNom();
           cbenseignant.setValue(nomu);
        }
    }
    @FXML
    void modifierMatiere(ActionEvent event) {


        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {
            Matiere m=listMatiere.getSelectionModel().getSelectedItem();
            int nbHeure=m.getNbrHeure();
            int credit=m.getCredit();
            if(m!=null){
                m.setCoefficient(Integer.valueOf(tfcoeff.getText()));
                m.setNbrHeure(Integer.valueOf(tfnbrheures.getText()));
                m.setCredit(Integer.valueOf(tfcredit.getText()));
                m.setNomM(tfnommatiere.getText());
                int idu=su.getIdByNomUser(cbenseignant.getValue());
                m.setIdEnseignant(idu);
               // m.setIdEnseignant(1);
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
