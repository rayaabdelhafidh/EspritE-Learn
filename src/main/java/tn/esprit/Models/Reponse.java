package tn.esprit.Models;

import java.util.ArrayList;

public class Reponse {
    int id;
    Quiz quiz;
    private ArrayList<String> responses;
    private int StudentId;
    private String SelectedOption;
    public Reponse(){

    }

    public Reponse(int id, Quiz quiz, ArrayList<String> responses) {
        this.id = id;
        this.quiz = quiz;
        this.responses = responses;
        this.StudentId=StudentId;
        this.SelectedOption =SelectedOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelectedOption() {
        return SelectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        SelectedOption = selectedOption;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public ArrayList<String> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<String> responses) {
        this.responses = responses;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }
}
