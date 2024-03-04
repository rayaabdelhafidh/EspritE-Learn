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


}
}
