package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class CodeQRController {
    @FXML
    private ImageView imageView;



    public void displayQRCode(String filePath) {
        try {
            File file = new File(filePath);
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            imageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
