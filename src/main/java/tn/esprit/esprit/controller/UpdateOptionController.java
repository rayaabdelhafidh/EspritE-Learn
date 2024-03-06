package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Services.ServiceOption;

public class UpdateOptionController {
    @FXML
    private TextField optiondTF;
    @FXML
    private TextField isscorrTF;

    @FXML
    private TextField optioncTF;

    @FXML
    private TextField questiTF;
    @FXML
    private Button updateButton;

    private Option selectedOption;
    private ServiceOption serviceOption;
    Question question=new Question();
    public void initData(Option option) {
        this.selectedOption = option;

        optioncTF.setText(option.getOption_content());
        isscorrTF.setText(String.valueOf(option.isIs_correct()));

    }

    public void setServiceOption(ServiceOption serviceOption) {
        this.serviceOption = serviceOption;
    }

@FXML
    public void updateOption() {
        ServiceOption serviceOption1=new ServiceOption();
        // Mettre à jour les détails de l'option avec les valeurs des champs de texte

        selectedOption.setOption_content(optioncTF.getText());
        selectedOption.setIs_correct(Boolean.parseBoolean(isscorrTF.getText()));


        // Appeler la méthode de mise à jour du service avec l'option mise à jour
        serviceOption1.update(selectedOption);
        System.out.println("Option mise à jour avec succès.");
        // Fermer la fenêtre de mise à jour
    }




}
