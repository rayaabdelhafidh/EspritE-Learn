package tn.esprit;

import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.Services.ServiceOption;
import tn.esprit.Services.ServiceQuestion;
import tn.esprit.Services.ServiceQuiz;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Quiz quiz1 = new Quiz(10,"simple quiz",25,"subject");
        ServiceQuiz serviceQuiz=new ServiceQuiz();
        serviceQuiz.add(quiz1);
        System.out.println("Quiz ajouté avec succès !");
        Question question1 = new Question(11,"simple question",quiz1);
        ServiceQuestion serviceQuestion=new ServiceQuestion();
        serviceQuestion.add(question1);
        System.out.println("Question ajoutée avec succès !");
        ServiceOption serviceOption=new ServiceOption();
        Option   option1 = new Option(5,"Option 1", true,question1);
        serviceOption.add(option1);
        Option option2 = new Option(196,"Option 2", false, question1);
        serviceOption.add(option2);
        System.out.println("option ajoutée avec succès !");


    }
}