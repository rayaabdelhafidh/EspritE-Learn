package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.Presence;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestPresence_VueAdmin implements Initializable {


    @FXML
    private ListView<Presence> listViewAjout;

    private void loadDate() {
        ServicePresence servicePresence = new ServicePresence();
        List<Presence> presences = servicePresence.getAllwithouId();
        listViewAjout.setItems(FXCollections.observableArrayList(presences));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
        sendEmailAtScheduledTime();
    }

    void sendMaill() {
        // Define the path where the PDF file will be saved
        String outputPath = "C:\\Users\\21629\\Downloads\\output.pdf";

        // Generate the PDF file
        PDFGenerator pdfGenerator = new PDFGenerator();
        List<Presence> presences = listViewAjout.getItems(); // Assuming listViewAjout contains Presence objects
        pdfGenerator.generatePDFFromListView(presences, outputPath);

        // Prepare email details
        String from = "omaimabhd3@gmail.com"; // Sender's email address
        String password = "vjgj bvoy jaio ghjo"; // Sender's email password
        String to = "islem1192002@gmail.com"; // Recipient's email address
        String subject = "Presence PDF"; // Email subject
        String message = "Please find attached the presence PDF file."; // Email message

        // Send the email with the PDF attachment
        Mail.send(from, password, to, subject, message, outputPath);

        // Show confirmation message
        showAlert(Alert.AlertType.INFORMATION, "Email Sent", "The presence PDF has been sent successfully!");
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void sendEmailAtScheduledTime() {
        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the time until 18:00
        LocalTime scheduledTime = LocalTime.of(16, 35);
        LocalDateTime scheduledDateTime = LocalDateTime.of(now.toLocalDate(), scheduledTime);

        // If the current time is after 18:00, schedule the email for the next day
        if (now.toLocalTime().isAfter(scheduledTime)) {
            scheduledDateTime = scheduledDateTime.plusDays(1);
        }

        // Calculate the delay until the scheduled time
        long delay = Duration.between(now, scheduledDateTime).toMillis();

        // Schedule the task to send the email at the scheduled time
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            // Call your sendMail method here
            sendMaill();
        }, delay, TimeUnit.MILLISECONDS);
    }


    @FXML
    void sendMailPdf(ActionEvent event) {
        sendMaill();

    }





}





