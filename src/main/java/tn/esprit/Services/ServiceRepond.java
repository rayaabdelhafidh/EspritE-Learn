package tn.esprit.Services;

import tn.esprit.Models.Reponse;
import tn.esprit.interfaces.IServices;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class ServiceRepond implements IServices<Reponse> {
    private Connection cnx;
    public ServiceRepond(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void add(Reponse reponse) {
        String query = "INSERT INTO responses (student_id, quiz, selected_option) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);

                stm.setInt(1, reponse.getStudentId());
                stm.setInt(2, reponse.getQuiz().getQuiz_id());
                stm.setString(3, reponse.getSelectedOption());
                stm.executeUpdate();


        }
        catch (SQLException e) {
                System.out.println(e.getMessage());

        }
    }

    @Override
    public ArrayList<Reponse> getAll() {
        return null;
    }

    @Override
    public void update(Reponse reponse) {

    }

    @Override
    public boolean delete(Reponse reponse) {
        return false;
    }
}
