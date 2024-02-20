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
        return null;
    }

    @Override
    public void update(Option option) {

    }

    @Override
    public boolean delete(Option option) {
        return false;
    }
}
