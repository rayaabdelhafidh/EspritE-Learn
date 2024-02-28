package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Matiere;
import tn.esprit.models.Niveau;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServicePlanDetude;
import javafx.scene.input.MouseEvent;
import java.sql.SQLException;

public class AjouterPlanDetude {

    @FXML
    private ComboBox<Niveau> cbniveau;

    @FXML
    private TextField tfnomprogramme;

    @FXML
    private TextField tfcreditsrequistotal;

    @FXML
    private TextField tfdureetotal;
    @FXML
    private ListView<PlanDetude> listPlanDetude;

    ObservableList<PlanDetude> data= FXCollections.observableArrayList();
     ServicePlanDetude sp=new ServicePlanDetude();


     @FXML
     public void initialize(){
         cbniveau.getItems().setAll(Niveau.values());
          refresh();
     }

    public String controleDeSaisire(){
        String erreur="";
        if(tfnomprogramme.getText().isEmpty()){
            erreur+="-Nom programme vide vide\n";
        }

        if(cbniveau.getValue()==null){
            erreur+="-Niveau vide\n";
        }


        return erreur;
    }
    @FXML
    void ajouterPlanDetude(ActionEvent event) {
        //ps.add(new Personne(0,nomtf.getText(),prenomTF.getText(),Integer.parseInt(ageTF.getText())));
        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {
            PlanDetude p=new PlanDetude();
            p.setNomProgramme(tfnomprogramme.getText());
            p.setNiveau(cbniveau.getValue());
            sp.ajouter(p);
            refresh();
        }

    }


    @FXML
    void fillforum(MouseEvent event) {
     PlanDetude p=listPlanDetude.getSelectionModel().getSelectedItem();
     if(p!=null){
         tfnomprogramme.setText(p.getNomProgramme());
         cbniveau.setValue(p.getNiveau());
         tfdureetotal.setText(String.valueOf(p.getDureeTotal()));
         tfcreditsrequistotal.setText(String.valueOf(p.getCreditsRequisTotal()));
     }
    }

    @FXML
    void modifierPlanDetude(ActionEvent event) {
        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {
            PlanDetude p=listPlanDetude.getSelectionModel().getSelectedItem();
            p.setNomProgramme(tfnomprogramme.getText());
            p.setNiveau(cbniveau.getValue());
            sp.modifier(p);
            refresh();
        }

    }

    @FXML
    void supprimerPlanDetude(ActionEvent event) {
         PlanDetude p=listPlanDetude.getSelectionModel().getSelectedItem();
         if(p!=null){
             sp.supprimer(p.getId());
             refresh();
         }
    }
    public void refresh(){
        data.clear();
        data=FXCollections.observableArrayList(sp.afficher());
        listPlanDetude.setItems(data);
    }
}
