package tn.esprit.Models;

public class Question {
    private int questionId ;
    private String content;
    private int score;

    private Quiz quiz;
    public Question() {
    }




    public Question(int questionId, String content,  Quiz quiz,int score) {
        this.questionId = questionId;
        this.content = content;
        this.quiz = quiz;
        this.score = score;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return   content+ "                                                       " +score  ;
    }

}
