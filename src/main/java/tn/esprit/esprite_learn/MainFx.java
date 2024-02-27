package tn.esprit.esprite_learn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AjoutClub.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Gestion Club!");
            stage.setScene(scene);
            stage.show();
        }
         catch (IOException e) {
            System.out.println(e.getMessage());
             e.printStackTrace();
         }
    }
}
