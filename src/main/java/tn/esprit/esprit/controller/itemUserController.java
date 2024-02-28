package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class itemUserController implements Initializable {

    @FXML
    private Button btnDeleteUser;

    @FXML
    private Label labelEmailUser;

    @FXML
    private Label labelNomUser;

    @FXML
    private Label labelRoleUser;


    User user;

    public void setData (User user){
        this.user = user ;
        labelNomUser.setText(user.getNom());
        labelEmailUser.setText(user.getEmail());
        labelRoleUser.setText(user.getRole());
    }

    public User getData (User userr){
        this.user=userr;
        return this.user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private int id;
    @FXML
    void supprimerUser(ActionEvent event) {
        ServiceUser as = new ServiceUser();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet utilisateur ?");
        Optional<ButtonType> result = alert.showAndWait();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemUser.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                itemUserController itemController = fxmlLoader.getController();
                this.id=itemController.getData(user).getUser_id();
                System.out.println(this.id);
            } catch (IOException ex) {
                Logger.getLogger(itemUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        if (result.isPresent() && result.get() == ButtonType.OK) {
            as.Supprimer(id);
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Utilisateur supprimé avec succés.");
            Optional<ButtonType> result2 = alert.showAndWait();

        }
    }
}
