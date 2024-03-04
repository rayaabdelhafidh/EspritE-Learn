package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.esprit.MainFx;
import tn.esprit.esprit.iservice.OnChangeListener;
import tn.esprit.esprit.models.Cour;
import tn.esprit.esprit.services.ServiceCour;
import tn.esprit.esprit.services.ServiceMatiere;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class AjouterCour implements OnChangeListener {

    @FXML
    private ComboBox<String> cbmatiere;

    @FXML
    private TextField tfcour;

    @FXML
    private AnchorPane anchorpane;



    @FXML
    private GridPane grid;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfduree;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tfobjectif;

    @FXML
    private TextField tftitre;
    int idModifier;
    ServiceCour sc=new ServiceCour();
    ServiceMatiere sm=new ServiceMatiere();
    public void initialize() {

        cbmatiere.getItems().setAll(sm.getNomMatiere());
        refresh();
        tfcour.setDisable(true);
        tfimage.setDisable(true);
    }

    public String controleDeSaisire(){
        String erreur="";
        if(tftitre.getText().isEmpty()){
            erreur+="-Titre vide\n";
        }
        if(tfobjectif.getText().isEmpty()){
            erreur+="-Objectif vide\n";
        }
        if(tfcour.getText().isEmpty()){
            erreur+="-Url cour vide\n";
        }
        if(tfimage.getText().isEmpty()){
            erreur+="-Image vide\n";
        }
        if(tfdescription.getText().isEmpty() || tfdescription.getText().length()>200){
            erreur+="-Description vide ou invalide (max:200 caractere)\n";
        }
        if(cbmatiere.getValue()==null){
            erreur+="-Matiere vide\n";
        }
        /*if(tfduree.getText().isEmpty() || !tfduree.getText().matches("^(2[1-9]|3[0-9]|4[0-2])$")){

            erreur+="-Duree doit etre un nombre entre 21 et 42\n";
        }*/
        if(tfduree.getText().isEmpty() || !tfduree.getText().matches("\\d+")){

            erreur+="-Duree doit etre un nombre positif\n";
        }
        return erreur;
    }
    @FXML
    void ajouterCour(ActionEvent event) {
        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else{Cour c=new Cour();
            c.setTitre(tftitre.getText());
            c.setObjectif(tfobjectif.getText());
            c.setDuree(Integer.valueOf(tfduree.getText()));
            c.setImage(tfimage.getText());
            c.setCoursPdfUrl(tfcour.getText());
            c.setDescription(tfdescription.getText());
            int idMatiere= sm.getIdByName(cbmatiere.getValue());
            c.setIdMatiere(idMatiere);
            System.out.println(c);
            sc.ajouter(c);
            Notifications.create().title("Ajouter cour").text("Ajout cour avec succes").hideAfter(Duration.seconds(5)).showInformation();
            refresh();}

    }
    @FXML
    void modifierCour(ActionEvent event) {
        String erreur=controleDeSaisire();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide!");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else {Cour c=new Cour();
            c.setId(idModifier);
            c.setTitre(tftitre.getText());
            c.setObjectif(tfobjectif.getText());
            c.setDuree(Integer.valueOf(tfduree.getText()));
            c.setImage(tfimage.getText());
            c.setCoursPdfUrl(tfcour.getText());
            c.setDescription(tfdescription.getText());
            int idMatiere= sm.getIdByName(cbmatiere.getValue());
            c.setIdMatiere(idMatiere);
            System.out.println(c);
            sc.modifier(c);
            refresh();}

    }


    public void refresh(){
         grid.getChildren().clear();//bch nfasa5 ili f wost l matrice lkol
        Set<Cour> cours=sc.afficher();
        int column=0;
        int row=1;
        for (Cour c:cours){
          try {
              //  bch n3ayt ll carte w n3abi les information
              //or l carte mawjoud f interface anchorpane
              FXMLLoader card=new FXMLLoader(MainFx.class.getResource("/cour-card-view.fxml"));
              AnchorPane anchorPane=card.load();//7atyna l card f interface vu que l grid fiha des interfaces
              CourCardView item=card.getController();
              item.remplireData(c);
              item.setOnChangeListener(this);
              if(column==2){
                  column=0;
                  row++;
              }
              grid.add(anchorPane,column++  ,row);
              GridPane.setMargin(anchorPane,new Insets(10));
          } //psk l fichier ynajm ykoun mouch mwjoud maynajmch ylowdi interface mt3 fich mch mawjoud
          catch (IOException ex){

          }
        }

    }


    @Override
    public void onSupprimerClicked() {
        refresh();
    }

    @Override
    public void onModdifierClicked(Cour c) {
      idModifier=c.getId();
      tfcour.setText(c.getCoursPdfUrl());
      tfdescription.setText(c.getDescription());
      tfduree.setText(String.valueOf(c.getDuree()));
      tfimage.setText(c.getImage());
      tftitre.setText(c.getTitre());
      tfobjectif.setText(c.getObjectif());
      cbmatiere.setValue(sm.getById(c.getId()).getNomM());
    }

    @FXML
    void uploadPDF(ActionEvent event) {
       // la classe FileChooser, qui est une boîte de dialogue permettant à l'utilisateur de sélectionner un fichier sur le système de fichiers.
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfcour.getScene().getWindow());
        if(file!=null){
            String filename= file.getName();
            tfcour.setText(filename);
        }
    }

    @FXML
    void uploadimage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfcour.getScene().getWindow());
        if(file!=null){
            String filename= file.getName();
            tfimage.setText(filename);
        }
    }
}
