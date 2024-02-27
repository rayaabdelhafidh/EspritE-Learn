package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Services.ServiceClub;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierClub {

    @FXML
    private TextField ActiviteTF;

    @FXML
    private Button AfficherBtn;

    @FXML
    private DatePicker DateTF;

    @FXML
    private TextArea DescriptionTF;

    @FXML
    private Button ModifierBtn;

    @FXML
    private TextField NbMembresTF;

    @FXML
    private RadioButton NonBT;

    @FXML
    private TextField nomTF;

    @FXML
    private RadioButton ouiBT;

    private String NomClub;

    private AfficherClub af;

    public void setAfficherClub(AfficherClub af) {
        this.af = af;
    }

    @FXML
    void AfficherClub(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClub.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Affichage");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading AfficherClub.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setClub(Clubs c) {
        this.NomClub = c.getNomClub();
        nomTF.setText(c.getNomClub());
        LocalDate localDate = Instant.ofEpochMilli(c.getDateFondation().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DateTF.setValue(localDate);
        ActiviteTF.setText(c.getTypeActivite());
        DescriptionTF.setText(c.getDescription());
        NbMembresTF.setText(String.valueOf(c.getNbMembres()));
        if (c.isActive()) {
            ouiBT.setSelected(true);
            NonBT.setSelected(false);
        } else {
            ouiBT.setSelected(false);
            NonBT.setSelected(true);
        }
    }

    @FXML
    void ModifierClub(ActionEvent event) {
        try {
            // Fetch user data from the database
            ServiceClub SM = new ServiceClub();
            Clubs c = SM.find(NomClub);

            // Check if any field is empty
            if (nomTF.getText().isEmpty() || DescriptionTF.getText().isEmpty() || ActiviteTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
                return;
            }

            // Handling Date
            Date DateFondation;
            try {
                LocalDate localDate = DateTF.getValue();
                if (localDate != null) {
                    if (localDate.isAfter(LocalDate.now())) {
                        showAlert(Alert.AlertType.ERROR, "Error", "La date de fondation ne peut pas être postérieure à la date actuelle!");
                        return;
                    }
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    DateFondation = Date.from(instant);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Il est obligatoire de mettre la date de fondation!");
                    return;
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Le champ date est invalide");
                return;
            }

            // Update user data
            c.setNomClub(nomTF.getText());
            c.setDescription(DescriptionTF.getText());
            c.setTypeActivite(ActiviteTF.getText());
            c.setNbMembres(Integer.parseInt(NbMembresTF.getText()));
            c.setDateFondation(DateFondation);
            c.setActive(ouiBT.isSelected());

            // Call the modifier method to update the user
            SM.update(c);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Modification réussie !!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating user data!");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}