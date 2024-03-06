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
        String qry = "INSERT INTO `question`(content , quiz_id,score) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, question.getContent());

            stm.setInt(2, question.getQuiz().getQuiz_id());
            stm.setInt(3,question.getScore());
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
                int questionId = rs.getInt("questionId");
                String content = rs.getString("content");

                int quiz_id = rs.getInt("quiz_id");
                int score=rs.getInt("score");

                // Créer un objet Question
                Question question = new Question(questionId, content, new Quiz(quiz_id),score);

                // Ajouter la question à la liste
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  questions;
    }

    @Override
    public void update(Question question) {
        String qry = "UPDATE question SET content = ?, quiz_id = ?, score = ? WHERE questionId = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, question.getContent());

            stm.setInt(2, question.getQuiz().getQuiz_id());
            stm.setInt(3,question.getScore());
            stm.setInt(4, question.getQuestionId());

            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(Question question) {

        String qry = "DELETE FROM question WHERE questionId = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, question.getQuestionId());
            stm.executeUpdate();
        return true;}
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }
    public List<Question> getQuestionsByQuizId(int quiz_id) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM question WHERE quiz_id = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1,quiz_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String content = resultSet.getString("content");
                int score=resultSet.getInt("score");
                // Vous pouvez récupérer d'autres attributs de la question si nécessaire

                // Créer un objet Question
                Question question = new Question(questionId, content, new Quiz(quiz_id),score);
                questions.add(question);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions: " + e.getMessage());
        }
        return questions;
    }
    public int getQuestionScore(int questionId) {
        int score = 0;
        String query = "SELECT score FROM question WHERE questionId = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                score = resultSet.getInt("score");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du score de la question: " + e.getMessage());
        }
        return score;
    }
}
