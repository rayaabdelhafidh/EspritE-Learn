package tn.esprit.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {
    @FXML
    private TextField emailf;

    @FXML
    private PasswordField passf;
    private final ServiceUser su = new ServiceUser();

    public int idSession;

    @FXML
    public void login(ActionEvent event) throws IOException, SQLException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Récupération de l'email et du mot de passe saisis par l'utilisateur
        String email = emailf.getText();
        String password = passf.getText();

        // Validation des champs email et mot de passe
        if (!validateFields(email, password)) {
            new Alert(Alert.AlertType.ERROR, "Tous les champs sont obligatoires", ButtonType.CLOSE).show();
            return;
        }

        // Validation de la syntaxe de l'email
        if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Veuillez entrer un email valide", ButtonType.CLOSE).show();
            return;
        }

        // Authentification de l'utilisateur et récupération du rôle
        String loginResult = su.login(email, password);
        if (loginResult.equals("Admin")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/DashbordAdmin.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else if (loginResult.equals("Etudiant")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/ProfileEtudiant.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else if (loginResult.equals("Enseignant")) {
            stage.close();
            Parent page2 = FXMLLoader.load(getClass().getResource("/ProfileEnseignant.fxml"));
            Scene scene2 = new Scene(page2);
            stage.setScene(scene2);
            stage.show();
            idSession = su.GetIDByEmail(email);
        } else {
            new Alert(Alert.AlertType.ERROR, loginResult, ButtonType.CLOSE).show();
        }
        // Affichage de l'alerte de connexion réussie après les opérations d'authentification
        new Alert(Alert.AlertType.INFORMATION, "Connexion réussie!", ButtonType.OK).show();
    }


    // Méthode pour valider les champs email et mot de passe
    private boolean validateFields(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    // Méthode pour valider la syntaxe de l'email
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    // Méthode pour naviguer vers une nouvelle scène
    // Méthode pour naviguer vers une nouvelle scène
    private void navigateToScene(ActionEvent event, String sceneName) {
        try {
            // Chargement de la nouvelle scène à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            Parent root = loader.load();

            // Création de la scène
            Scene scene = new Scene(root);

            // Récupération de la fenêtre actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Gestion de l'erreur de chargement du fichier FXML
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement de la scène", ButtonType.CLOSE).show();
        } catch (NullPointerException e) {
            // Gestion de l'erreur de référence nulle
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fichier FXML introuvable", ButtonType.CLOSE).show();
        }
    }
    @FXML
    private void GoToregister(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent root = loader.load();
            emailf.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
