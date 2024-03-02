package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.iservice.OnChangeListener;
import tn.esprit.models.Cour;
import tn.esprit.service.ServiceCour;
import tn.esprit.service.ServiceMatiere;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CourCardEtudiant {


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
    ServiceCour sc=new ServiceCour();


    Cour cour;
    public void remplireData(Cour c) {
        cour = c; //nstha9ha bch n3abi l cours fl refresh nst7a9ha fl modif w supp
        ltitre.setText(c.getTitre());
        lduree.setText(String.valueOf(c.getDuree()));
        lobjectif.setText(c.getObjectif());
        ldescription.setText(c.getDescription());
        lmatiere.setText(sm.getById(c.getIdMatiere()).getNomM());
        File file = new File("D:\\Esprit_Learn - Copy\\src\\main\\resources\\images\\" + c.getImage());
        Image image = new Image(file.toURI().toString());
        img.setImage(image);
    }
    @FXML
    public void initialize() {

    }
    @FXML
    void telechargerPDF(ActionEvent event) {
        File file=new File("D:\\Esprit_Learn - Copy\\src\\main\\resources\\pdf\\" + cour.getCoursPdfUrl());
        if(file.exists()){
            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    }
