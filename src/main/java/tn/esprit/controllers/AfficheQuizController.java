package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import okhttp3.*;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class AfficheQuizController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button sortButton;

    @FXML
    private URL location;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Quiz> listView;
    @FXML
    private Button AjouterButton;
    Question question;
    @FXML
    private Button btt;


    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    private ServiceQuiz serviceQuiz=new ServiceQuiz();
    private ServiceQuestion serviceQuestion=new ServiceQuestion();


    // Assurez-vous de définir le serviceQuestion

    @FXML
    public void initialize(URL url,ResourceBundle resourceBundle) {
        // Chargez les données depuis le service et ajoutez-les à la table
        ArrayList<Quiz> quizzes = serviceQuiz.getAll();

        listView.getItems().addAll(quizzes);

        AjouterButton.setOnAction(event -> {
             // Vérifie si un élément est cliqué une fois
                Quiz quiz = listView.getSelectionModel().getSelectedItem(); // Récupère l'objet Quiz sélectionné
                if (quiz != null) {
                    int quiz_id = quiz.getQuiz_id(); // Récupère l'ID du quiz
                    System.out.println("ID du quiz sélectionné : " + quiz_id);
                    List<Question> questions = serviceQuestion.getQuestionsByQuizId(quiz_id);
                    System.out.println("ID du quiz sélectionné : " +  questions);
                    // Vous pouvez utiliser cet ID pour l'interface addQuestion
                    // Par exemple, vous pouvez passer cet ID à votre interface addQuestion
                    try {
                        openAddQuestion(quiz_id); // Appel de la méthode pour ouvrir l'interface addQuestion
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        });
       btt.setOnAction(event -> {
           { // Vérifie si un élément est cliqué une fois
                Quiz quiz = listView.getSelectionModel().getSelectedItem(); // Récupère l'objet Quiz sélectionné
                if (quiz != null) {
                    int quiz_id = quiz.getQuiz_id(); // Récupère l'ID du quiz

                    System.out.println("ID du quiz sélectionné : " + quiz_id);
                    openAffiche(quiz_id);


                }
            }
        });
        updateButton.setOnAction(event -> {
            Quiz selectedQuiz = listView.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null) {
                openUpdateInterfaces(selectedQuiz);
            }
        });
        deleteButton.setOnAction(event -> {
            Quiz selectedQuiz = listView.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null) {
                deleteQuiz(selectedQuiz);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.toLowerCase(); // Convertir le texte en minuscules pour la recherche insensible à la casse

            // Filtrer la liste des quizzes en fonction du texte de recherche
            List<Quiz> filteredQuizzes = quizzes.stream()
                    .filter(quiz -> quiz.getDescription().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            // Effacer la ListView et réafficher les quizzes filtrés
            listView.getItems().clear();
            listView.getItems().addAll(filteredQuizzes);
        });


    }
    public void openAddQuestion(int quizId)throws IOException {
        // Code pour ouvrir l'interface addQuestion et passer quizId
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addQuestion.fxml"));
        Parent root = loader.load();
        AddQuestionController addQuestionController = loader.getController();
        addQuestionController.setQuiz_id(quizId);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Afficher la nouvelle interface addQuestion
    }

    public void openUpdateInterfaces( Quiz selectedQuiz){
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateQuiz.fxml"));
            Parent root = loader.load();
            UpdateQuizController controller = loader.getController();
            // Passer l'option sélectionnée au contrôleur de la nouvelle interface
            controller.setDat(selectedQuiz);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("UpdateQuiz");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void deleteQuiz(Quiz quiz) {
        boolean deleted = serviceQuiz.delete(quiz);
        if (deleted) {
            listView.getItems().remove(quiz); // Supprimez l'question de la liste affichée dans la ListView
            System.out.println("Question supprimée avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression de l'quiz.");
        }

    }
    public void openAffiche(int quiz_id){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheReponse.fxml"));
            Parent root = loader.load();
            AfficheReponseController controller=loader.getController();

            controller.setQuiz_id(quiz_id);
            controller.setServiceQuestion(serviceQuestion);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions List");
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }





    //@FXML
  //  void handleTableClick(MouseEvent event) {
        // Gérez les événements de clic sur la table ici
      //  Quiz quiz = tableView.getSelectionModel().getSelectedItem();
        //if (quiz != null) {
         //   int quiz_id = quiz.getQuiz_id();
            // Faites quelque chose avec l'ID du quiz sélectionné
          //  loadAddQuestionView(quiz_id);

        //}
  //  }
  //  @FXML
   // public void loadAddQuestionView(int quiz_id) {
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddQuestion.fxml"));
       // Parent root;
       // try {
         //   root = loader.load();

            // Récupérer l'objet Quiz à partir de son ID
            //Quiz selectedQuiz = serviceQuiz.getQuizById(quiz_id); // Assurez-vous d'avoir une méthode pour récupérer un quiz par son ID
            //AddQuestionController addQuestionController = loader.getController();

            // Définir l'objet Quiz dans le contrôleur AddQuestionController


            //Stage stage = new Stage();
          //  stage.setScene(new Scene(root));
           // stage.show();
        //} catch (IOException e) {
          //  e.printStackTrace();
        //}
    //}




}

