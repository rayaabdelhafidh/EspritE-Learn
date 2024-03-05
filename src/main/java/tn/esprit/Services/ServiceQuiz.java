package tn.esprit.Services;

import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.Models.Quiz;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.Services.ServiceQuestion;
public class ServiceQuiz implements IServices<Quiz> {
    ServiceQuestion serviceQuestion = new ServiceQuestion();
    ServiceOption serviceOption=new ServiceOption();
    private Connection cnx ;
    public ServiceQuiz(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(Quiz quiz) {
        String qry = "INSERT INTO `quizz`( description,    matiere) VALUES (?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,quiz.getDescription());

            stm.setString(2,quiz.getMatiere());
            stm.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Quiz> getAll() {
        ArrayList<Quiz> quizzes = new ArrayList();
        String qry ="SELECT * FROM `quizz`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Quiz q = new Quiz();
                q.setQuiz_id(rs.getInt("quiz_id"));
                q.setDescription(rs.getString("description"));

                q.setMatière(rs.getString("matiere"));
                quizzes.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }



    @Override
    public void update(Quiz quiz) {
        String qry = "UPDATE quizz SET description = ?, matiere = ? WHERE quiz_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, quiz.getDescription());

            stm.setString(2, quiz.getMatiere());
            stm.setInt(3, quiz.getQuiz_id());

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
    public Quiz getQuizById(int quiz_id) {
        String qry = "SELECT * FROM `quizz` WHERE quiz_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, quiz_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Quiz q = new Quiz();
                q.setQuiz_id(rs.getInt("quiz_id"));
                q.setDescription(rs.getString("description"));

                q.setMatière(rs.getString("matiere"));
                return q;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Return null if quiz with given id is not found
    }
    public ArrayList<Quiz> searchByDescription(String query) {
        ArrayList<Quiz> searchResults = new ArrayList<>();
        if (query.isEmpty()) {
            return searchResults; // Retourner tous les quiz si la recherche est vide
        } else {
            for (Quiz quiz : searchResults) {
                if (quiz.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(quiz);
                }
            }
        }
        return searchResults;
    }

}
