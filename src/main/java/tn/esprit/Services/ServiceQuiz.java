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
        String qry = "INSERT INTO `quizz`( description,  enseignantId,  matiere) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,quiz.getDescription());

            stm.setInt(2,quiz.getEnseignantId());
            stm.setString(3,quiz.getMatiere());
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
                q.setDescription(rs.getString("description"));
                q.setEnseignantId(rs.getInt("enseignantId"));
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

    }

    @Override
    public boolean delete(Quiz quiz) {
        return false;
    }
}
