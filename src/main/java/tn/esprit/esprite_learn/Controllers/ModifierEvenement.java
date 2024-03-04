package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierEvenement {

    @FXML
    private TextArea AfficheTF;

    @FXML
    private Button AfficherBtn;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextField LieuTF;

    @FXML
    private Button ModifierBtn;

    @FXML
    private TextField PrixTF;

    @FXML
    private ComboBox<String> clubCombo;

    @FXML
    private TextField nomTF;

    @FXML
    private Button upload;

    String NomEvenement;


    public void setEvenement(Evenement c) throws SQLException {
        this.NomEvenement = c.getNomEvenement();
        nomTF.setText(c.getNomEvenement());
        LocalDate localDate = Instant.ofEpochMilli(c.getDateEvenement().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DateTF.setValue(localDate);
        LieuTF.setText(c.getLieuEvenement());
        AfficheTF.setText(c.getAfficheEvenement());
        PrixTF.setText(String.valueOf(c.getPrixEvenement()));
        ServiceClub SM = new ServiceClub();
        clubCombo.getItems().setAll(SM.getNom());
    }
    @FXML
    void AfficherEvenement(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenement.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            Stage newStage = new Stage();
            newStage.setTitle("Evenements!");
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Error loading AfficherEvenement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ModifierEvenement(ActionEvent event) {
        try {
            // Fetch user data from the database
            ServiceEvenement SM = new ServiceEvenement();
            Evenement c = SM.find(NomEvenement);

            // Check if any field is empty
            if (nomTF.getText().isEmpty() || LieuTF.getText().isEmpty() || AfficheTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
                return;
            }

            // Handling Date
            Date DateFondation;
            try {
                LocalDate localDate = DateTF.getValue();
                if (localDate != null) {
                    if (localDate.isBefore(LocalDate.now())) {
                        showAlert(Alert.AlertType.ERROR, "Error", "La date de event ne peut pas être anterieure à la date actuelle!");
                        return;
                    }
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    DateFondation = Date.from(instant);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Il est obligatoire de mettre la date de event!");
                    return;
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Le champ date est invalide");
                return;
            }
            ServiceClub sc=new ServiceClub();
            int idMatiere= sc.getIdByName(clubCombo.getValue());

            // Update user data
            c.setNomEvenement(nomTF.getText());
            c.setAfficheEvenement(AfficheTF.getText());
            c.setLieuEvenement(LieuTF.getText());
            c.setPrixEvenement(Integer.parseInt(PrixTF.getText()));
            c.setDateEvenement(DateFondation);
            c.setClub(idMatiere);

            // Call the modifier method to update the user
            SM.update(c);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Modification réussie !!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating user data!");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String error, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the file chooser dialog
        Stage stage = (Stage) AfficheTF.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Update AfficheTF with the selected image file path
        if (selectedFile != null) {
            AfficheTF.setText(selectedFile.getAbsolutePath());
            // Update the Evenement object with the image path
        }
    }

}
