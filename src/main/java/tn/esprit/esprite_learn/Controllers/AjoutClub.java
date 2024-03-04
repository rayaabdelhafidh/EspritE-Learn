package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class AjoutClub {

            @FXML
            private TextField ActiviteTF;

            @FXML
            private Button AjouterBtn;
             @FXML
            private Button AfficherBtn;

            @FXML
            private DatePicker DateTF;

            @FXML
            private TextArea DescriptionTF;

            @FXML
            private TextField NbMembresTF;

            @FXML
            private RadioButton NonBT;

            @FXML
            private TextField nomTF;

            @FXML
            private RadioButton ouiBT;
            @FXML
            private void AjoutClub(ActionEvent event) throws Exception {
                // Nom
                String NomClub = nomTF.getText();
// Vérification du champ nom
                if (NomClub.trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie: champ NOM est vide");
                    alert.setHeaderText(null);
                    alert.setContentText("Il est obligatoire d'entrer le nom du club! ");
                    alert.showAndWait();
                    return;
                }

// Date
                Date DateFondation = null;
                try {
                    LocalDate localDate = DateTF.getValue();
                    if (localDate.isAfter(LocalDate.now())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de saisie: la date sélectionnée est posterieure à la date actuelle");
                        alert.setHeaderText(null);
                        alert.setContentText("La date de fondation du club ne peut pas être posterieure à la date actuelle !");
                        alert.showAndWait();
                        return;
                    } else if (localDate != null) {
                        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                        DateFondation = Date.from(instant);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de saisie: champ DATE est vide");
                        alert.setHeaderText(null);
                        alert.setContentText("Il est obligatoire de mettre la date de fondation !");
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ date est invalide");
                    alert.showAndWait();
                    return;
                }

// Nbmembres
                int NbMembres = 0;
                try {
                    NbMembres = Integer.parseInt(NbMembresTF.getText());

                    // Vérification que le frais est positif
                    if (NbMembres < 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de saisie");
                        alert.setHeaderText(null);
                        alert.setContentText("Le nombre doit être un entier positif");
                        alert.showAndWait();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ nombre doit être un entier");
                    alert.showAndWait();
                    return;
                }

// Active
                Boolean Active = null;
                if (ouiBT.isSelected() && NonBT.isSelected()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie: deux etats séléctionnés ");
                    alert.setHeaderText(null);
                    alert.setContentText("Il faut cocher une seule etat !!");
                    alert.showAndWait();
                    return;
                } else if (ouiBT.isSelected()) {
                    Active = true;
                } else if (NonBT.isSelected()) {
                    Active = false;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ état est obligatoire");
                    alert.showAndWait();
                    return;
                }
                // TypeActivite
                String TypeActivite = ActiviteTF.getText();
// Vérification du champ typeActivite
                if (TypeActivite.trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie: champ Type est vide");
                    alert.setHeaderText(null);
                    alert.setContentText("Il est obligatoire d'entrer le type d'activite du club! ");
                    alert.showAndWait();
                    return;
                }

                // Description
                String Description = DescriptionTF.getText();

// Vérification du champ description
                if (Description.trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie: champ description est vide");
                    alert.setHeaderText(null);
                    alert.setContentText("Il est obligatoire d'entrer la description du club! ");
                    alert.showAndWait();
                    return;
                }

                DataBase db = DataBase.getInstance();
                Clubs c = new Clubs(0, NomClub, DateFondation, TypeActivite, Description, NbMembres, Active);
                ServiceClub sc = new ServiceClub();
                sc.add(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout effectué");
                alert.setHeaderText(null);
                alert.setContentText("Le club est ajoutée avec succées");
                alert.showAndWait();
                nomTF.clear();
                DateTF.getEditor().clear(); //Type: datePeaker
                NbMembresTF.clear();
                DescriptionTF.clear();
                ActiviteTF.clear();
                ouiBT.setSelected(false);//Type: RadioButton
                NonBT.setSelected(false);//type: RadioButton

            }

         @FXML
         private void AfficherClub(ActionEvent event) {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClub.fxml"));
             try{
                 Parent root = fxmlLoader.load();
                 Scene scene = new Scene(root);
                 Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
                 currentStage.close(); // Close the current stage
                 Stage newStage = new Stage();
                 newStage.setTitle("Clubs!");
                 newStage.setScene(scene);
                 newStage.show();
             }
             catch (IOException e) {
                 System.out.println("Error loading AfficherClub.fxml: " + e.getMessage());
                 e.printStackTrace();
             }
         }

            void initialize() {
                assert NbMembresTF != null : "fx:id=\"NbMembresTF\" was not injected: check your FXML file 'AjoutClub.fxml'.";
                assert nomTF != null : "fx:id=\"nomTF\" was not injected: check your FXML file 'AjoutClub.fxml'.";

            }



}
