package tn.esprit.Services;

import tn.esprit.Models.Option;
import tn.esprit.Models.Question;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class ServiceOption implements IServices<Option> {
    private Connection cnx ;
    ServiceQuestion serviceQuestion=new ServiceQuestion();
    public  ServiceOption(){
        cnx = MyDataBase.getInstance().getCnx();
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
}
