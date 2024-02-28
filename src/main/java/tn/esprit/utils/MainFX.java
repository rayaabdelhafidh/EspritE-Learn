package tn.esprit.utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.FormulaireEmploi;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormulaireEmploi.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Gestion emploi");
//        primaryStage.setScene(scene);
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterSalle.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gestion Salles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

