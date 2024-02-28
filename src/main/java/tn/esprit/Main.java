package tn.esprit;

import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceOption;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Quiz quiz1 = new Quiz(10, "simple quiz", 25, "subject");
        ServiceQuiz serviceQuiz = new ServiceQuiz();
        serviceQuiz.add(quiz1);
        System.out.println("Quiz ajouté avec succès !");
        Question question1 = new Question(14, "simple question", quiz1);
        ServiceQuestion serviceQuestion = new ServiceQuestion();
        serviceQuestion.add(question1);
        System.out.println("Question ajoutée avec succès ! Identifiant de la question : " + question1.getQuestionId());
        Option option1 = new Option(5, "Option 1", true, question1);
        ServiceOption serviceOption = new ServiceOption();
        serviceOption.add(option1);
        System.out.println("Option ajoutée avec succès ! Identifiant de la question associée à l'option : " + option1.getQuestion().getQuestionId());
        Option option2 = new Option(196, "Option 2", false, question1);
        serviceOption.add(option2);
        System.out.println("Option ajoutée avec succès ! Identifiant de la question associée à l'option : " + option2.getQuestion().getQuestionId());


        ArrayList<Quiz> allQuizzes = serviceQuiz.getAll();
        for (Quiz quiz : allQuizzes) {
            System.out.println("Description: " + quiz.getDescription());
            System.out.println("Enseignant ID: " + quiz.getEnseignantId());
            System.out.println("Matière: " + quiz.getMatiere());
            System.out.println("-----------------------------");
        }
        // Call the getAll() method to retrieve all questions
        ArrayList<Question> allQuestions = serviceQuestion.getAll();
        // Print out the retrieved questions or do whatever testing you need
        for (Question question : allQuestions) {
            System.out.println("Question ID: " + question.getQuestionId());
            System.out.println("Content: " + question.getContent());
            System.out.println("Quiz ID: " + question.getQuiz().getQuiz_id());
            System.out.println("-----------------------------");
        }
        // Call the getAll() method to retrieve all options
        ArrayList<Option> allOptions = serviceOption.getAll();

        // Print out the retrieved options or do whatever testing you need
        for (Option option : allOptions) {
            System.out.println("Option ID: " + option.getOption_id());
            System.out.println("Option Content: " + option.getOption_content());
            System.out.println("Is Correct: " + option.isIs_correct());
            System.out.println("-----------------------------");
        }
        int quizIdToRetrieve = 3; // ID de quiz à récupérer
        Quiz quiz = serviceQuiz.getQuizById(quizIdToRetrieve);

        // Vérifier si le quiz a été récupéré avec succès
        if (quiz != null) {
            System.out.println("Quiz trouvé !");
            System.out.println("ID du Quiz: " + quiz.getQuiz_id());
            System.out.println("Description: " + quiz.getDescription());
            System.out.println("ID de l'Enseignant: " + quiz.getEnseignantId());
            System.out.println("Matière: " + quiz.getMatiere());
        } else {
            System.out.println("Aucun quiz trouvé pour l'ID: " + quizIdToRetrieve);
        }

        Option optionToUpdate = new Option();
        Question question = new Question();
        optionToUpdate.setOption_id(101); // Remplacez 1 par l'ID de l'option que vous voulez mettre à jour
        optionToUpdate.setOption_content("Nouveau contenu de l'option");
        optionToUpdate.setIs_correct(true);
        // ou false selon votre besoin
        optionToUpdate.setQuestion(question1);
        serviceOption.update(optionToUpdate);

        Question questionToUpdate = new Question();
        questionToUpdate.setContent("Nouveau contenu de la question");
        questionToUpdate.setQuiz(quiz1);
        questionToUpdate.setQuestionId(14);
        serviceQuestion.update(questionToUpdate);
        Quiz quizToUpdate = new Quiz();
        quizToUpdate.setDescription("Nouvelle description du quiz");
        quizToUpdate.setEnseignantId(123); // ID de l'enseignant
        quizToUpdate.setMatière("Nouvelle matière du quiz");
        quizToUpdate.setQuiz_id(10); // ID du quiz à mettre à jour

        // Créez une instance de votre classe de service ou d'accès aux données
        ServiceQuiz serviceQuiz1 = new ServiceQuiz(); // Remplacez VotreClasseDeService par le nom de votre classe

        // Appelez la méthode update
        serviceQuiz1.update(quizToUpdate);
        Quiz quizToDelete = new Quiz();
        quizToDelete.setQuiz_id(106); // Remplacez 123 par l'ID du quiz que vous souhaitez supprimer

        // Appelez la méthode delete avec l'objet Quiz
        boolean deleted = serviceQuiz1.delete(quizToDelete);

        // Vérifiez si la suppression a réussi
        if (deleted) {
            System.out.println("Suppression réussie !");
        } else {
            System.out.println("Échec de la suppression.");
        }


        List<Question> questions = serviceQuestion.getQuestionsByQuizId(10);

        // Vérifier si la liste de questions n'est pas vide


        // Afficher les questions récupérées
        for (Question question3 : questions) {
            System.out.println("Question ID: " + question.getQuestionId());
            System.out.println("Content: " + question.getContent());
            // Vous pouvez afficher d'autres attributs si nécessaire
        }

    }
    }
