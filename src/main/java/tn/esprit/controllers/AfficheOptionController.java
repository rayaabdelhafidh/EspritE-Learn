package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.Models.Option;
import tn.esprit.Services.ServiceOption;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AfficheOptionController {

    @FXML
    private ListView<Option> listView;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;


    private ServiceOption serviceOption=new ServiceOption();
    @FXML
    public void initialize(){

       ArrayList<Option> optionss=serviceOption.getAll();
        listView.getItems().addAll(optionss);
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
            Option option  = listView.getSelectionModel().getSelectedItem();
                if (option != null) {
                    int option_id = option.getOption_id();
                    System.out.println("ID du option sélectionné : " + option_id);}

            }
        });
        // Your existing initialization code...
        updateButton.setOnAction(event -> {
            Option selectedOption = listView.getSelectionModel().getSelectedItem();
            if (selectedOption != null) {
                openUpdateInterface(selectedOption);
            }
        });
        deleteButton.setOnAction(event -> {
            Option selectedOption = listView.getSelectionModel().getSelectedItem();
            if (selectedOption != null) {
                deleteOption(selectedOption);
            }
        });
    }
    private void openUpdateInterface(Option selectedOption) {
       try {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Update.fxml"));
        Parent root = loader.load();
           UpdateOptionController controller = loader.getController();
           // Passer l'option sélectionnée au contrôleur de la nouvelle interface
           controller.initData(selectedOption);


           Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.setTitle("Update Option");
           stage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    void deleteOption(Option option) {
        boolean deleted = serviceOption.delete(option);
        if (deleted) {
            listView.getItems().remove(option); // Supprimez l'option de la liste affichée dans la ListView
            System.out.println("Option supprimée avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression de l'option.");
        }

    }


    }



