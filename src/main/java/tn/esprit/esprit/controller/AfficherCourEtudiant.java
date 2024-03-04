package tn.esprit.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.esprit.MainFx;
import tn.esprit.esprit.models.Cour;
import tn.esprit.esprit.services.ServiceCour;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class AfficherCourEtudiant {
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> cbtri;
    @FXML
    private TextField tfrecherche;
    @FXML
    public void initialize() {
        refresh(sc.afficher());
        cbtri.getItems().setAll("Titre","Description","Duree");
        recherche_avance();
    }
    ServiceCour sc=new ServiceCour();
    public void refresh(Set<Cour> cours) {
        grid.getChildren().clear();//bch nfasa5 ili f wost l matrice lkol
        // Set<Cour> cours=sc.afficher();
        int column = 0;
        int row = 1;
        for (Cour c : cours) {
            try {
                //  bch n3ayt ll carte w n3abi les information
                //or l carte mawjoud f interface anchorpane
                FXMLLoader card = new FXMLLoader(MainFx.class.getResource("/cour-card-etudiant.fxml"));
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

    @FXML
    void tri(ActionEvent event) {
        refresh(sc.sortByCritere(cbtri.getValue()));
    }

    public void recherche_avance(){
        refresh(sc.afficher());
        ObservableList<Cour> data= FXCollections.observableArrayList(sc.afficher());
        FilteredList<Cour> filteredList=new FilteredList<>(data, c->true);
        tfrecherche.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(c->{
                if(newValue.isEmpty()|| newValue==null){
                    return true;
                }
                if(c.getTitre().contains(newValue)){
                    return true;
                }
                else if(c.getDescription().contains(newValue)){
                    return true;
                }
                else if(String.valueOf(c.getDuree()).contains(newValue)){
                    return true;
                }

                else{
                    return false;
                }
            });

            refresh(new HashSet<>(filteredList));
        });
    }
}
