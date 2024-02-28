package tn.esprit.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.DetailedWeekView;
import com.calendarfx.model.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.control.*;
import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Salle;
import tn.esprit.Services.ServiceSalle;

import java.util.Arrays;
import java.util.stream.Stream;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AjouterEmploi implements Initializable {

    @FXML
    private DetailedWeekView emploi;

    @FXML
    private Button enregistrerEmploi;

    @FXML
    private ListView<String> matieresList = new ListView<>();

    private Calendar birthdays;
    private Calendar holidays;

    @FXML
    private Label dates;

    @FXML
    private Label titre;

    @FXML
    private ComboBox<Bloc> blocListe;

    @FXML
    private ComboBox<Integer> listeSalles;

    private LocalDate startDate;
    private LocalDate endDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        blocListe.getItems().addAll(FXCollections.observableArrayList(Bloc.values()));
        ServiceSalle ss = new ServiceSalle();
        ArrayList<Salle> salles = ss.afficherTousSalles();
        ObservableList<Integer> numSalles = FXCollections.observableArrayList();
        for (Salle salle : salles) {
            numSalles.add(salle.getNumeroSalle());
        }
        listeSalles.setItems(numSalles);
        birthdays = new Calendar("Birthdays");
        holidays = new Calendar("Holidays");

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

        emploi.getCalendarSources().add(myCalendarSource);
        matieresList.setOnDragDetected(event -> {
            Dragboard dragboard = matieresList.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(matieresList.getSelectionModel().getSelectedItem());
            dragboard.setContent(content);
        });
        emploi.setOnDragOver(event -> {
            if (event.getGestureSource() != emploi && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        emploi.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                Entry<String> eventEntry = new Entry<>(dragboard.getString());
                birthdays.addEntry(eventEntry);

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
            updateDateLabel();

        });

    }

    public void setMatieresList(ObservableList<String> selectedMatieres) {
        matieresList.setItems(FXCollections.observableArrayList(selectedMatieres));
    }
    private void updateDateLabel() {
        if (startDate != null && endDate != null) {
            dates.setText(startDate.toString() + " - " + endDate.toString());
        }
    }
    public void setWeekDates(LocalDate startDate, LocalDate endDate) {
        Date premierDate = java.sql.Date.valueOf(startDate);
        Date dernierDate = java.sql.Date.valueOf(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        updateDateLabel();
    }

}



