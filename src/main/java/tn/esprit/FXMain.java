package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/ajouterPlanDetude.fxml"));
        //a7na fl etat mt3na hadhi l interface f wosto  AncherPane mais najmo nty7o f des cas ili mana3rfouch ismha fl interface donc dima a7na kif bch ndeclariw l root ili bch nloadiw fih dima madhabina nistanciw ml parent
        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter PlanDetude");
        primaryStage.show();*/
     //FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/ajouterPlanDetude.fxml"));
        //FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/ajouterMatiere.fxml"));
       //FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/modifierPlanDetude.fxml"));
      // FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/ajouterCour.fxml"));
       FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/gestion_Cour_Admin.fxml"));
       // FXMLLoader loader=new FXMLLoader(FXMain.class.getResource("/gestion-Cour-Etudiant.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter Cour");
        primaryStage.show();



    }
}
