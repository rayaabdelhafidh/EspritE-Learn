package tn.esprit.Services;

import tn.esprit.Models.Question;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;
import tn.esprit.Models.Quiz;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceQuestion implements IServices<Question> {
    private Connection cnx ;

    public ServiceQuestion() {
        cnx =MyDataBase.getInstance().getCnx();
    }

    @Override
    // ajouter une question dans la bd
    public void add(Question question) {
        //1 - req SQL done
        String qry = "INSERT INTO `question`(content , quiz_id) VALUES (?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, question.getContent());
            stm.setInt(2, question.getQuiz().getQuiz_id());
            stm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Question> getAll() {
        ArrayList<Question> questions = new ArrayList();
        String qry ="SELECT * FROM `question`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String content = rs.getString("content");
                int quizId = rs.getInt("quiz_id");

                // Créer un objet Question
                Question question = new Question(questionId, content, new Quiz(quizId));

                // Ajouter la question à la liste
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  questions;
    }

    @Override
    public void update(Quiz quiz) {
        String qry = "UPDATE quizz SET description = ?, enseignantId = ?, matiere = ? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, quiz.getDescription());
            stm.setInt(2, quiz.getEnseignantId());
            stm.setString(3, quiz.getMatiere());
            stm.setInt(4, quiz.getQuiz_id());

            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean delete(Quiz quiz) {

        String qry = "DELETE FROM quizz WHERE quiz_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,quiz.getQuiz_id());
            stm.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
}
