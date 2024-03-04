package tn.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.Models.classe;

public class ClassCardView {
    @FXML
    private Label lfiliere;

    @FXML
    private Label lnbretudiant;

    @FXML
    private Label lniveau;

    @FXML
    private Label lnomclasse;

    public void remplireData(classe c){
       // cour=c; //nstha9ha bch n3abi l cours fl refresh nst7a9ha fl modif w supp
          lniveau.setText(String.valueOf(c.getNiveaux()));
          lfiliere.setText(String.valueOf(c.getFiliere()));
          lnbretudiant.setText(String.valueOf((c.getNbreEtud())));
          lnomclasse.setText(c.getNomClasse());

//        ltitre.setText(c.getTitre());
//        lduree.setText(String.valueOf(c.getDuree()));
//        lobjectif.setText(c.getObjectif());
//        ldescription.setText(c.getDescription());
//        lmatiere.setText(sm.getById(c.getIdMatiere()).getNomM());
//        File file=new File("D:\\Esprit_Learn - Copy\\src\\main\\resources\\images\\"+c.getImage());
//        Image image=new Image(file.toURI().toString());
//        img.setImage(image);
}
}
