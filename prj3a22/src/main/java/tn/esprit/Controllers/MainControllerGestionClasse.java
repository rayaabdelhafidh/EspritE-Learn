package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainControllerGestionClasse implements Initializable {

    @FXML
    private Tab classeTab;

    @FXML
    private Tab presenceTab;



    private void loadClasseController() {

    }

    private void loadPresenceController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Presence.fxml"));
            Parent root = loader.load();
            presenceTab.setContent(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadClasseController();
        loadPresenceController();

    }


}
