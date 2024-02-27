package tn.esprit.esprite_learn.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.utils.DataBase;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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


    private AfficherClub af;

    public void setAfficherClub(AfficherClub af){
        this.af=af;
    }

        @FXML
        void AfficherClub(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherClub.fxml"));
            try{
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Affichage");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                System.out.println("Error loading AfficherClub.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @FXML
        void ModifierClub(ActionEvent event) {

            Clubs c=null;

            c=af.onSelectedItem();
            nomTF.setText(c.getNomClub());
            Date date = c.getDateFondation();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        }


