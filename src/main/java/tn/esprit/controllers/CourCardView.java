package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Cour;
import tn.esprit.service.ServiceMatiere;

import java.io.File;

public class CourCardView {
    @FXML
    private ImageView img;

    @FXML
    private Label ldescription;

    @FXML
    private Label lduree;

    @FXML
    private Label lmatiere;

    @FXML
    private Label lobjectif;

    @FXML
    private Label ltitre;
    ServiceMatiere sm=new ServiceMatiere();
    public void remplireData(Cour c){

        ltitre.setText(c.getTitre());
        lduree.setText(String.valueOf(c.getDuree()));
        lobjectif.setText(c.getObjectif());
        ldescription.setText(c.getDescription());
        lmatiere.setText(sm.getById(c.getIdMatiere()).getNomM());
        File file=new File("D:\\Esprit_Learn - Copy\\src\\main\\resources\\images\\"+c.getImage());
        Image image=new Image(file.toURI().toString());
        img.setImage(image);

    }

}