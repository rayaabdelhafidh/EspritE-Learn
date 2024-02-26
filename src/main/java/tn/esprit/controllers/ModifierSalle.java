package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;
import javafx.scene.control.Button;

public class ModifierSalle {

    @FXML
    private Button modifier;

    @FXML
    private TextField idSalle;

    @FXML
    private ComboBox<Bloc> newBloc;

    @FXML
    private TextField newNum;

    ObservableList<Bloc> blocList = FXCollections.observableArrayList(Bloc.values());
    private final ServiceSalle ss = new ServiceSalle();

    @FXML
    void modifierSalle(ActionEvent event) {
//ss.modifierSalle(new Salle(bloc.get));

//       ss.ajouterSalle(new Salle(bloc.getValue(),Integer.parseInt(numSalle.getText())));
    }

}
