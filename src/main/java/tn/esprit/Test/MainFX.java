package tn.esprit.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterClub.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            Stage primaryStage = null;
            primaryStage.setTitle("gestion club");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
