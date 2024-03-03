package tn.esprit.Controllers;

import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.IServices.ServiceClasse;
import tn.esprit.IServices.ServicePersonne;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PresenceController implements Initializable {


    @FXML
    public CheckBox checkBoxPresent;
    @FXML
    private ListView<Personne> listViewEtudiants;
    @FXML
    private ListView<Presence> listViewAjout;
    @FXML
    private TextField nometudiant;
    @FXML
    private DatePicker dateAjoutPr;
    @FXML
    private ChoiceBox<String> ajoutSeance;
    @FXML
    private ChoiceBox<String> nomDesClasses;
    @FXML
    private ListView<Personne> affichageEtatPresence;
    @FXML
    private CheckBox checkBoxAbsent;

    @FXML
    private ComboBox<String> filtreClasse;
    private ServicePresence servicePresence;
    @FXML
    private RadioButton radioAbsent;

    @FXML
    private RadioButton radioPresent;

    private ToggleGroup toggleGroup;

    public PresenceController() {
        this.servicePresence = new ServicePresence();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendEmailAtScheduledTime();
        toggleGroup = new ToggleGroup();
        radioPresent.setToggleGroup(toggleGroup);
        radioAbsent.setToggleGroup(toggleGroup);

        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

        ObservableList<String> presenceList = FXCollections.observableArrayList();
        for (Seance s : Seance.values()) {
            presenceList.add(s.toString());
        }
        ajoutSeance.setItems(presenceList);
        ServiceClasse scl = new ServiceClasse();
        ArrayList<String> classes = scl.getNomClasses();
        ObservableList<String> classeList = FXCollections.observableArrayList(classes);
        nomDesClasses.setItems(classeList);
        loadDate();
        ServicePersonne SCP=new ServicePersonne();
        List<Personne> etudiants = SCP.getEtudiantsAvecPresence(String.valueOf(nomDesClasses));
        for (Personne etudiant : etudiants) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(etudiant.getNom() + " " + etudiant.getPrenom());
            // Définissez l'état de la CheckBox en fonction de l'état de présence de l'étudiant
            checkBox.setSelected(etudiant.getEtatPresence() == EtatPresence.Present);
            // Ajoutez un gestionnaire d'événements pour mettre à jour l'état de présence lorsqu'une CheckBox est cochée ou décochée
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    etudiant.setEtatPresence(EtatPresence.Present);
                } else {
                    etudiant.setEtatPresence(EtatPresence.Absent);
                }
                // Appelez une méthode pour enregistrer l'état de présence dans la base de données
                SCP.enregistrerEtatPresence(etudiant.getIdP(), etudiant.getEtatPresence());
            });
            EnregistrerEtatPresence(null);
            // Ajoutez la CheckBox à votre interface utilisateur
            //   AnchorPane.getChildren().add(checkBox);
        }
        ServiceClasse serviceClasse = new ServiceClasse();
        ArrayList<String> classees = serviceClasse.getNomClasses();
        ObservableList<String> classeListe = FXCollections.observableArrayList(classes);
        filtreClasse.setItems(classeList);
        loadDatePresence();
        // Add event handler for filtreClasse
        filtreClasse.setOnAction(this::classeSelectedEtatPresence);




    }


    @FXML
    void EnregistrerEtatPresence(ActionEvent event) {
        if (verifierSaisiesEnregistrerEtat()) {
            // Récupérer l'étudiant sélectionné, la date de la présence et la séance depuis l'interface utilisateur
            Personne etudiantSelectionne = listViewEtudiants.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = dateAjoutPr.getValue();
            Seance selectedSeance = ajoutSeance.getValue() != null ? Seance.valueOf(ajoutSeance.getValue()) : null;

            // Enregistrer l'état de présence dans la base de données
            if (etudiantSelectionne != null && selectedDate != null && selectedSeance != null) {
                // Enregistrer l'état de présence dans la base de données
                EtatPresence etatPresence = toggleGroup.getSelectedToggle().isSelected() ? EtatPresence.Absent : EtatPresence.Present;
                ServicePersonne servicePersonne = new ServicePersonne();
                servicePersonne.enregistrerEtatPresence(etudiantSelectionne.getIdP(), etatPresence);

                // Actualiser l'interface utilisateur
                showAlert(Alert.AlertType.INFORMATION, "Confirmation", "L'état de présence a été enregistré avec succès !");
            }
        }
    }

    @FXML
    public void CreatePresence(ActionEvent event) {
        if (!verifierSaisies()) {
            return;
        }

        String selectedClasseName = nomDesClasses.getValue();
        ServiceClasse serviceClasse = new ServiceClasse();
        classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);

        if (selectedClasse == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Classe non trouvée !");
            return;
        }

        LocalDate localDate = dateAjoutPr.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        Seance seance = Seance.valueOf(ajoutSeance.getValue());
        String etatPresence = checkBoxAbsent.isSelected() ? "Absent" : "Présent";

        Presence presence = new Presence(date, seance, selectedClasse.getNomClasse());

        try {
            servicePresence.addPresence(presence);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la présence : " + e.getMessage());
        }
        showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "La présence est effectuée avec succès !");
    }

    private boolean verifierSaisies() {
        String selectedClasseName = nomDesClasses.getValue();

        if (selectedClasseName == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une classe !");
            return false;
        }

        LocalDate localDate = dateAjoutPr.getValue();
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une date valide !");
            return false;
        }

        if (ajoutSeance.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une séance !");
            return false;
        }

        return true;
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDate() {
        ServicePresence servicePresence = new ServicePresence();
        List<Presence> presences = servicePresence.getAllwithouId();
        listViewAjout.setItems(FXCollections.observableArrayList(presences));
    }

    public void classeSelected(ActionEvent mouseEvent) {
        String selectedClasseName = nomDesClasses.getValue();

        if (selectedClasseName != null) {
            ServiceClasse serviceClasse = new ServiceClasse();
            classe selectedClasse = serviceClasse.getClasseByNom(selectedClasseName);

            if (selectedClasse != null) {
                ServicePersonne servicePersonne = new ServicePersonne();
                List<Personne> etudiants = servicePersonne.getEtudiantsDeLaClasse(selectedClasse);
                listViewEtudiants.getItems().clear();
                listViewEtudiants.getItems().addAll(etudiants);
            }
        }
    }
    private void loadDatePresence() {
        String selectedClasseName = nomDesClasses.getValue();
        ServicePersonne servicePersonne = new ServicePersonne();
        if (selectedClasseName != null) {

            List<Personne> etudiantsAvecPresence = servicePersonne.getEtudiantsAvecPresencePourClasse(selectedClasseName);
            affichageEtatPresence.getItems().clear();
            affichageEtatPresence.getItems().addAll(etudiantsAvecPresence);

        }else {

            affichageEtatPresence.getItems().addAll(servicePersonne.getAll());
        }
    }


    @FXML
    void classeSelectedEtatPresence(ActionEvent event) {
        String selectedClasseName = filtreClasse.getValue();
        if (selectedClasseName != null) {
            ServicePersonne servicePersonne = new ServicePersonne();
            // Retrieve students with their presence status for the selected class
            List<Personne> etudiantsAvecPresence = servicePersonne.getEtudiantsAvecPresencePourClasse(selectedClasseName);
            // Update the affichageEtatPresence ListView with the retrieved data
            affichageEtatPresence.getItems().clear();
            affichageEtatPresence.getItems().addAll(etudiantsAvecPresence);
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une classe !");
        }
    }











    @FXML
    void fillFormPresence(MouseEvent event) {
        Personne selectedPersonne = listViewEtudiants.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null) {
            nometudiant.setText(selectedPersonne.getNom());
        }
    }


    private boolean verifierSaisiesEnregistrerEtat() {
        // Récupérer l'étudiant sélectionné, la date de la présence et la séance depuis l'interface utilisateur
        Personne etudiantSelectionne = listViewEtudiants.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = dateAjoutPr.getValue();
        Seance selectedSeance = ajoutSeance.getValue() != null ? Seance.valueOf(ajoutSeance.getValue()) : null;

        // Vérifier si un étudiant est sélectionné
        if (etudiantSelectionne == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un étudiant !");
            return false;
        }

        // Vérifier si une date est sélectionnée
        if (selectedDate == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une date !");
            return false;
        }

        // Vérifier si une séance est sélectionnée
        if (selectedSeance == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une séance !");
            return false;
        }

        return true;
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
        String to = "ahmedbhd97@gmail.com"; // Recipient's email address
        String subject = "Presence PDF"; // Email subject
        String message = "Please find attached the presence PDF file."; // Email message

        // Send the email with the PDF attachment
        Mail.send(from, password, to, subject, message, outputPath);

        // Show confirmation message
        showAlert(Alert.AlertType.INFORMATION, "Email Sent", "The presence PDF has been sent successfully!");
    }



    public void sendEmailAtScheduledTime() {
        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Calculate the time until 18:00
        LocalTime scheduledTime = LocalTime.of(19, 52);
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




}
