package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.FXMain;
import tn.esprit.models.Cour;
import tn.esprit.service.ServiceCour;

import java.io.IOException;
import java.util.Set;


public class AfficherCourEtudiant {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        refresh();
    }
    ServiceCour sc=new ServiceCour();
    public void refresh() {
        grid.getChildren().clear();//bch nfasa5 ili f wost l matrice lkol
        Set<Cour> cours = sc.afficher();
        int column = 0;
        int row = 1;
        for (Cour c : cours) {
            try {
                //  bch n3ayt ll carte w n3abi les information
                //or l carte mawjoud f interface anchorpane
                FXMLLoader card = new FXMLLoader(FXMain.class.getResource("/cour-card-etudiant.fxml"));
                AnchorPane anchorPane = card.load();//7atyna l card f interface vu que l grid fiha des interfaces
                CourCardEtudiant item = card.getController();
                item.remplireData(c);

                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            } //psk l fichier ynajm ykoun mouch mwjoud maynajmch ylowdi interface mt3 fich mch mawjoud
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
