package tn.esprit.Models;

import java.util.List;

public class Question {
    private int questionId ;
    private String content;

    private Quiz quiz;
    public Question() {
    }


    public Question(int questionId , String content, Quiz quiz) {
        this.questionId  = questionId ;
        this.content = content;
        this.quiz = quiz;
    }

    public Question(int questionId) {
        this.questionId = questionId;
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return   content  ;
    }

}
