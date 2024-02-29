package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import tn.esprit.utilse.MainFX;

import java.io.IOException;

public class GestionClasseAdmin {

    @FXML
    private AnchorPane mainLayer;

    @FXML
    void GestionClasse(ActionEvent event) {

        loadView("/Classe.fxml");}

    @FXML
    void GestionPresence(ActionEvent event) {
        loadView("/Presence.fxml");

    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxmlPath));
            Node view = loader.load();
            mainLayer.getChildren().setAll(view);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

