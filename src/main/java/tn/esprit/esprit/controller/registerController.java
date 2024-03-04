package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.esprit.esprit.Securite.BCrypt;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class registerController implements Initializable {

        @FXML
        private PasswordField cpassf;

        @FXML
        private TextField emailf;

        @FXML
        private PasswordField passf;

        @FXML
        private ChoiceBox<String> rolef;
        private String[] users={"Etudiant","Enseignant"};

        @FXML
        private TextField usernamef;
    private final ServiceUser su = new ServiceUser();





    @FXML
    private void register() throws SQLException {
        String nom = usernamef.getText();
        String mdp = passf.getText();
        String cmdp = cpassf.getText();
        String email = emailf.getText();
        String role = rolef.getValue();

        String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());

        // Perform validation checks (e.g., empty fields, password match, etc.)
        if (nom.isEmpty() || mdp.isEmpty() || cmdp.isEmpty() || email.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Format email non valide!");
            return;
        }

        if (su.EmailExiste(email)) {
            showAlert("Cet email est déjà enregistré!");
            return;
        }

        if (!mdp.equals(cmdp)) {
            showAlert("Les mots de passe ne correspondent pas!");
            return;
        }
        if (mdp.length() < 8) {
            showAlert("Le mot de passe doit contenir au moins 8 caractères!");
            return;
        }

        if (role == null || role.isEmpty()) {
            showAlert("Veuillez choisir un rôle!");
            return;
        }

        // If all validations pass, proceed with user registration
        User newUser = new User(nom, hashedPassword, email, role);
        // Call your service class method to add the user
        su.addUser(newUser);

        // Optionally, you can clear the input fields after registration
        usernamef.clear();
        passf.clear();
        cpassf.clear();
        emailf.clear();
        showAlert("Bienvenue!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    @FXML
    private void GoToLogin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            emailf.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            rolef.getItems().addAll(users);
            rolef.setOnAction(this::getuser);
    }
    public void getuser(ActionEvent event){
            String U=rolef.getValue();
    }
}


