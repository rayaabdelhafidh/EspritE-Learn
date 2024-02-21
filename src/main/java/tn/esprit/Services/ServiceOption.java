package tn.esprit.Services;

import tn.esprit.Models.Option;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        ArrayList<Option> options = new ArrayList<>();
        String qry = "SELECT * FROM options";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            ResultSet rs= stm.executeQuery();

            while (rs.next()) {
                Option option = new Option();
                option.setOption_id(rs.getInt("option_id"));
                option.setOption_content(rs.getString("option_content"));
                option.setIs_correct(rs.getBoolean("is_correct"));

                // Vous devez obtenir l'objet Question associé à cette option
                // Vous pouvez utiliser serviceQuestion.getById() pour obtenir l'objet Question
                int questionId = rs.getInt("questionId");


                options.add(option);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return options;
    }


    @Override
    public void update(Option option) {
        String qry = "UPDATE options SET option_content = ?, is_correct = ?, questionId = ? WHERE option_id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, option.getOption_content());
            stm.setBoolean(2, option.isIs_correct());
            stm.setInt(3, option.getQuestion().getQuestionId());
            stm.setInt(4, option.getOptionId());

            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(Option option) {
        String qry = "DELETE FROM options WHERE id = ?";
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
