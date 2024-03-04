package tn.esprit.esprite_learn.Controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.tax.Registration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Models.Participant;
import tn.esprit.esprite_learn.Services.ServicePaiement;
import tn.esprit.esprite_learn.Services.ServiceParticipant;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Acheter {

    @FXML
    private Button Confirmer;

    @FXML
    private TextField MM;

    @FXML
    private TextField Nom;

    @FXML
    private TextField YY;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button annuler;

    @FXML
    private Button back_btn;

    @FXML
    private TextField cvc;

    @FXML
    private TextField email;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView imageCard;

    @FXML
    private TextField num_card;

    @FXML
    private Label total;
    private float total_pay;

    private TextField spinnerTextField;
    private String uniqueToken;
    private Evenement evenement;

    public void setData(Evenement e) {
        this.evenement=e;
        total_pay = evenement.getPrixEvenement();
        int mm = LocalDate.now().getMonthValue();
        int yy = LocalDate.now().getYear();
        String total_txt = "Total : " + String.valueOf(total_pay) + " Dt.";
        total.setText(total_txt);

    }
    public void initialize(URL url, ResourceBundle rb) {
        anchor.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.getStylesheets().add(getClass().getResource("/main.projet/css/style.css").toExternalForm());
            }
        });
    }

    @FXML
    void payment(ActionEvent event) throws Exception {
        System.out.println(cvc.getText());
        if (Nom.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le champ Nom doit etre rempli!");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (email.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le champ email doit etre rempli");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (!isValidEmail(email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("L'adresse mail doit etre valide!");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (num_card.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Entrer votre numéro de carte");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (cvc.getText().length()<3 || cvc.getText().length()>3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("CVC doit contenir 3 chiffres");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else if (!check_expDate(Integer.parseInt(YY.getText()),Integer.parseInt( MM.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Entrer une date valide!");
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Nom.setStyle(null);
            email.setStyle(null);
            num_card.setStyle(null);
            cvc.setStyle(null);
            MM.setStyle(null);
            YY.setStyle(null);
            boolean isValid = check_card_num(num_card.getText());
            if (!isValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Entrer un numero de carte valide");
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                num_card.setStyle(null);
                String name = Nom.getText();
                String email_txt = email.getText();
                String num = num_card.getText();
                int yy = Integer.parseInt(YY.getText());
                int mm = Integer.parseInt(MM.getText());
                //String cvc_num = String.valueOf(cvc.getValue());
                boolean payment_result = ServicePaiement.processPayment(name, email_txt, total_pay, num, mm, yy, cvc.getText());
                if (payment_result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Successful Payment.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    this.uniqueToken = UUID.randomUUID().toString();
                    ServiceParticipant rs = new ServiceParticipant();
                    Participant participants = new Participant();
                    participants.setIduser(1);
                    //participants.setIduser(Account.Currentid);
                    participants.setIdevennement(evenement.getIdEvenement());
                    participants.setDate_reservation(new Date(System.currentTimeMillis()));
                    participants.setToken(uniqueToken);
                    rs.add(participants);
                    redirect_to_successPage();
                    System.out.println("Payment Success");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Payment Failed.");
                    alert.setTitle("Problem");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    redirect_to_FailPage();
                }
            }
        }
    }

    private boolean check_expDate(int value_y, int value_mm) {
        boolean valid = false;
        LocalDate date = LocalDate.now();
        if ((value_y >= date.getYear()) && (value_mm >= date.getMonthValue())) {
            valid = true;
        }
        return valid;
    }

    private boolean check_card_num(String cardNumber) {
        // Trim the input string to remove any leading or trailing whitespace
        cardNumber = cardNumber.trim();
        // Step 1: Check length
        int length = cardNumber.length();
        if (length < 13 || length > 19) {
            return false;
        }
        String regex = "^(?:(?:4[0-9]{12}(?:[0-9]{3})?)|(?:5[1-5][0-9]{14})|(?:6(?:011|5[0-9][0-9])[0-9]{12})|(?:3[47][0-9]{13})|(?:3(?:0[0-5]|[68][0-9])[0-9]{11})|(?:((?:2131|1800|35[0-9]{3})[0-9]{11})))$";
        // Create a Pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the credit card number
        Matcher matcher = pattern.matcher(cardNumber);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        // Trim the input string to remove any leading or trailing whitespace
        email = email.trim();
        // Regular expression pattern to match an email address
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the email address
        Matcher matcher = pattern.matcher(email);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }

    @FXML
    void retour(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/AfficherEvenementFront.fxml"));
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
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private void redirect_to_successPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/Success_page.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setTitle("Gestion Club!");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void redirect_to_FailPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tn/esprit/esprite_learn/Fail_page.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setTitle("Gestion Club!");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}

