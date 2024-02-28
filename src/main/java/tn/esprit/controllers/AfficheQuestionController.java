package tn.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

public class AfficheQuestionController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView<Question> listView;
    @FXML
    private Button updateButton;

    private ServiceQuestion serviceQuestion=new ServiceQuestion();

    @FXML
    public void initialize(URL url,ResourceBundle resourceBundle){
        ArrayList<Question> questions=serviceQuestion.getAll();
        listView.getItems().addAll(questions);
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {// Vérifie si un élément est cliqué une fois
                Question question = listView.getSelectionModel().getSelectedItem(); // Récupère l'objet Question sélectionné
                if (question != null) {
                    int questionId = question.getQuestionId(); // Récupère l'ID du question
                    System.out.println("ID du question sélectionné : " + questionId);

                    // Vous pouvez utiliser cet ID pour l'interface addOption
                    // Par exemple, vous pouvez passer cet ID à votre interface
                    try {
                        openAddOption(questionId); // Appel de la méthode pour ouvrir l'interface addQuestion
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        // Your existing initialization code...
        updateButton.setOnAction(event -> {
            Question selectedQuestion = listView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                openUpdateInterface(selectedQuestion);
            }
        });
        deleteButton.setOnAction(event -> {
            Question selectedQuestion = listView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                deleteQuestion(selectedQuestion);
            }
        });
    }
    public void openAddOption(int questionId)throws IOException {
        // Code pour ouvrir l'interface addOption et passer quiestionId
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addOption.fxml"));
        Parent root = loader.load();
        AddOptionController addOptionController = loader.getController();
        addOptionController.setQuestion_id(questionId);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Afficher la nouvelle interface addQuestion
    }
    public void openUpdateInterface( Question selectedQuestion){
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateQuestion.fxml"));
            Parent root = loader.load();
            UpdateQuestionController controller = loader.getController();
            // Passer l'option sélectionnée au contrôleur de la nouvelle interface
            controller.setData(selectedQuestion);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Option");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void deleteQuestion(Question question) {
        boolean deleted = serviceQuestion.delete(question);
        if (deleted) {
            listView.getItems().remove(question); // Supprimez l'question de la liste affichée dans la ListView
            System.out.println("Question supprimée avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression de l'question.");
        }

    }

}
