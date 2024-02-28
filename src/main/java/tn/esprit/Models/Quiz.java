package tn.esprit.Models;

import java.util.List;

public class Quiz {
    private int quiz_id;
    private String description;

    private int enseignantId;
    private String matiere;


    public Quiz(int  quiz_id, String description, int enseignantId, String matière) {
        this. quiz_id =  quiz_id;
        this.description = description;

        this.enseignantId = enseignantId;
        this.matiere = matière;

    }

    public Quiz() {
    }

    public Quiz(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }





    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getMatiere() {
        return matiere;
    }

    public void setMatière(String matière) {
        this.matiere = matière;
    }

    @Override
    public String toString() {
        return    description + "                                                       " + matiere ;
    }
}
