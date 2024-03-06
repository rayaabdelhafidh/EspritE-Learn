package tn.esprit.Services;

import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOption implements IServices<Option> {
    private Connection cnx ;
    private ServiceQuestion serviceQuestion;

    public  ServiceOption(){
        cnx = MyDataBase.getInstance().getCnx();
        serviceQuestion = new ServiceQuestion();
    }
    @Override
    public void add(Option option) {
        String qryy = "INSERT INTO `options`( option_content, is_correct, questionId)VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qryy);
            stm.setString(1,option.getOption_content());
            stm.setBoolean(2,option.isIs_correct());
            stm.setInt(3,option.getQuestion().getQuestionId());

            stm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public ArrayList<Option> getAll() {
        ArrayList<Option> optionss = new ArrayList<>();
        String qry = "SELECT * FROM options";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs= stm.executeQuery(qry);

            while (rs.next()) {
                int option_id = rs.getInt("option_id");
                String option_content=rs.getString("option_content");
                Boolean is_correct= rs.getBoolean("is_correct");

                int questionId = rs.getInt("questionId");
                Option opp=new Option(option_id,option_content,is_correct,new Question(questionId));


                optionss.add(opp);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return optionss;
    }


    @Override
    public void update(Option option) {
        String qry = "UPDATE options SET option_content = ?, is_correct = ?, questionId = ? WHERE option_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, option.getOption_content());
            stm.setBoolean(2, option.isIs_correct());
            stm.setInt(3, option.getQuestion().getQuestionId());
            stm.setInt(4, option.getOption_id());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    }


    @Override
    public boolean delete(Option option) {
        String qry = "DELETE FROM options WHERE option_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, option.getOption_id());
            stm.executeUpdate();


            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public List<Option> getOptionByQuestion(int questionId) {
        List<Option> options = new ArrayList<>();
        String query = "SELECT * FROM options WHERE questionId = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int option_id = resultSet.getInt("option_id");
                String option_content = resultSet.getString("option_content");
                Boolean is_correct = resultSet.getBoolean("is_correct");
                // Utilisez le même objet Question pour toutes les options
                Option option = new Option(option_id, option_content,is_correct, new Question(questionId));
                options.add(option);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des questions: " + e.getMessage());
        }
        return options;
    }
    public Option getOptionById(int option_id){
        String qry="SELECT * FROM `options` WHERE option_id = ?";
        try{
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,option_id);
            ResultSet rs=stm.executeQuery();
            if (rs.next()){
                Option o=new Option();
                o.setOption_id(rs.getInt("option_id"));
                o.setOption_content(rs.getString("option_content"));
                o.setIs_correct(rs.getBoolean("is_correct"));
                return o;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean isOptionCorrect(int option_id){
        String sqlQuery = "SELECT is_correct FROM options WHERE option_id = ?";
        try{
            PreparedStatement stm = cnx.prepareStatement(sqlQuery);
            stm.setInt(1, option_id);
            ResultSet rs=stm.executeQuery();
            if (rs.next()){
                boolean  isCorrect =rs.getBoolean("is_correct");
                return isCorrect;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    public int getQuestionIdByOptionId(int option_id) {
        String query = "SELECT questionId FROM options WHERE option_id = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, option_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("questionId");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID de la question associée à l'option: " + e.getMessage());
        }
        return -1; // Retourne -1 en cas d'erreur ou si aucune question n'est trouvée
    }
    public int getQuestionScore(int questionId) {
        // Utilisez votre service de question pour récupérer le score de la question
        return serviceQuestion.getQuestionScore(questionId);
    }


}
