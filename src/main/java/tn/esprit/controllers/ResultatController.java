package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ResultatController {
    @FXML
    private TextField resulTF;
    public void setTotalScore(int totalScore) {
        resulTF.setText("Score Total: " + totalScore);
    }

}
