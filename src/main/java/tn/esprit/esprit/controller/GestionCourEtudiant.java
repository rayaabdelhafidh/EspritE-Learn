package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import tn.esprit.esprit.MainFx;

import java.io.IOException;

public class GestionCourEtudiant {

    @FXML
    private AnchorPane mainLayer;
    @FXML
    void gotogestionCour(ActionEvent event) {
        loadView("/afficher-cour-etudiant.fxml");
    }

    @FXML
    void gotogestionMatiere(ActionEvent event) {
        loadView("/afficher-matiere-etudiant.fxml");
    }

    @FXML
    void gotogestionPlanDetude(ActionEvent event) {
        loadView("/afficher-planDetude-etudiant.fxml");
    }
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFx.class.getResource(fxmlPath));
            Node view = loader.load();
            mainLayer.getChildren().setAll(view);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
