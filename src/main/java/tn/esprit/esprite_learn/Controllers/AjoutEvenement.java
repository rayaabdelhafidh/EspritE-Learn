package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AjoutEvenement {

    @FXML
    private TextArea AfficheTF;

    @FXML
    private Button AfficherBtn;

    @FXML
    private Button AjouterBtn;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField LieuTF;

    @FXML
    private TextField PrixTF;

    @FXML
    private ComboBox<String> clubCombo;

    @FXML
    private TextField nomTF;

    ServiceClub sm=new ServiceClub();

    public void initialize() {
            clubCombo.getItems().setAll(sm.getNom());

    }
    public AjoutEvenement() throws SQLException {
    }

    @FXML
    void AfficherEvenement(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenement.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Evenements!");
            newStage.setScene(scene);
            newStage.show();
        }
        catch (IOException e) {
            System.out.println("Error loading AfficherEvenement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void AjoutEvenement(ActionEvent event) throws Exception {
        // Nom
        String Nom = nomTF.getText();
// Vérification du champ nom
        if (Nom.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie: champ NOM est vide");
            alert.setHeaderText(null);
            alert.setContentText("Il est obligatoire d'entrer le nom de l'event! ");
            alert.showAndWait();
            return;
        }

// Date
        Date DateEvent = null;
        try {
            LocalDate localDate = DateTF.getValue();
            if (localDate.isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie: la date sélectionnée est anterieure à la date actuelle");
                alert.setHeaderText(null);
                alert.setContentText("La date de fondation du club ne peut pas être anterieure à la date actuelle !");
                alert.showAndWait();
                return;
            } else if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                DateEvent = Date.from(instant);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie: champ DATE est vide");
                alert.setHeaderText(null);
                alert.setContentText("Il est obligatoire de mettre la date de l'evenement !");
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

// Prix
        int prix = 0;
        try {
            prix = Integer.parseInt(PrixTF.getText());

            // Vérification que le frais est positif
            if (prix < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le prix doit être un entier positif");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le champ prix doit être un entier");
            alert.showAndWait();
            return;
        }

        // TypeActivite
        String Lieu = LieuTF.getText();
// Vérification du champ typeActivite
        if (Lieu.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie: champ Lieu est vide");
            alert.setHeaderText(null);
            alert.setContentText("Il est obligatoire d'entrer le lieu! ");
            alert.showAndWait();
            return;
        }

        // Description
        String affiche = AfficheTF.getText();

// Vérification du champ affiche
        if (affiche.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie: champ affiche est vide");
            alert.setHeaderText(null);
            alert.setContentText("Il est obligatoire d'entrer l'affiche! ");
            alert.showAndWait();
            return;
        }

        int idMatiere= sm.getIdByName(clubCombo.getValue());

        DataBase db = DataBase.getInstance();
        Evenement e = new Evenement();
        e.setNomEvenement(Nom);
        e.setLieuEvenement(Lieu);
        e.setDateEvenement(DateEvent);
        e.setClub(idMatiere);
        e.setAfficheEvenement(affiche);
        e.setPrixEvenement(prix);
        ServiceEvenement se = new ServiceEvenement();
        se.add(e);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout effectué");
        alert.setHeaderText(null);
        alert.setContentText("L'evenement est ajouté avec succées");
        alert.showAndWait();
        nomTF.clear();
        DateTF.getEditor().clear();
        PrixTF.clear();
        AfficheTF.clear();
        LieuTF.clear();
        clubCombo.getItems().clear();
    }
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) AfficheTF.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            AfficheTF.setText(selectedFile.getAbsolutePath());
        }
    }

}
