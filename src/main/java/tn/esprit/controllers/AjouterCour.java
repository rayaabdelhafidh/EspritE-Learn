package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Cour;
import tn.esprit.models.Matiere;
import tn.esprit.models.PlanDetude;
import tn.esprit.service.ServiceCour;
import tn.esprit.service.ServiceMatiere;

public class AjouterCour {

    @FXML
    private ComboBox<String> cbmatiere;

    @FXML
    private TextField tfcour;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfduree;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tfobjectif;

    @FXML
    private TextField tftitre;
    ServiceCour sc=new ServiceCour();
    ServiceMatiere sm=new ServiceMatiere();
    public void initialize() {
        cbmatiere.getItems().setAll(sm.getNomMatiere());
    }
    @FXML
    void ajouterCour(ActionEvent event) {
        Cour c=new Cour();
        c.setTitre(tftitre.getText());
        c.setObjectif(tfobjectif.getText());
        c.setDuree(Integer.valueOf(tfduree.getText()));
        c.setImage(tfimage.getText());
        c.setCoursPdfUrl(tfcour.getText());
        c.setDescription(tfdescription.getText());
        int idMatiere= sm.getIdByName(cbmatiere.getValue());
        c.setIdMatiere(idMatiere);
        System.out.println(c);
        sc.ajouter(c);

    }

}
