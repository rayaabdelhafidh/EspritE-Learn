package tn.esprit.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import tn.esprit.Models.classe;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifierClasse implements Initializable {
    public static classe cl;

    public  classe getCl() {
        return cl;
    }

    public static void setCl(classe cl) {
        ModifierClasse.cl = cl;
    }

    @FXML
    private TextField idDonnePourModif;

    @FXML
    private TextField labelNomModif;

    @FXML
    private ChoiceBox<?> lbFiliereModif;

    @FXML
    private TextField NbreEtudeLabel;

    @FXML
    private ChoiceBox<?> lbNiveauModif;

    @FXML
    private Button ModifierButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        classe cll1=new classe();
//List<classe> listCl = cll1.getidC();
        ObservableList<classe> list1= FXCollections.observableArrayList();
        for(classe cat : list1)
        {
            list1.add(cat);
        }

        {


        }



    }
}
