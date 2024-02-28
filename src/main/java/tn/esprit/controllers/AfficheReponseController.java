package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.Models.Question;
import tn.esprit.Services.ServiceQuestion;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AfficheReponseController implements Initializable {
    @FXML
    private ListView<Question> ListviewQuestion;
    ServiceQuestion serviceQuestion=new ServiceQuestion();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

}          }
