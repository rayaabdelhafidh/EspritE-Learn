package tn.esprit.esprit.services;

import tn.esprit.esprit.Securite.BCrypt;
import tn.esprit.esprit.interfaces.IUser;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceUser implements IUser<User> {
    Connection cnx;
    public ServiceUser(){
        cnx= MyDb.getInstance().getCnx();
    }

    @Override
    public void addUser(User user){
        String hashed = BCrypt.hashpw(user.getMdp(), BCrypt.gensalt());
        try {
            String qry = "INSERT INTO `user`( `nom`, `mdp`, `email`, `role`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(qry);

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getMdp());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());



            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User inserted successfully: " + user.getNom());
            } else {
                System.out.println("Failed to insert user: " + user.getNom());
            }
        } catch (Exception e) {
            System.out.println("Error while inserting user: " + e.getMessage());
        }
    }
    @Override
    public boolean EmailExiste(String email){

        String qry="SELECT COUNT(*) AS count FROM `user` WHERE email=?";
        try{
            PreparedStatement preparedStatement=cnx.prepareStatement(qry);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }catch (Exception e){
            System.out.println("Error while checking email existence: " + e.getMessage());
        }
        return false;
    }
    @Override
    public void registerUser(User user) {
        if (!EmailExiste(user.getEmail())) {
            String encryptmdp = encrypt(user.getMdp());
            user.setMdp(encryptmdp);
            addUser(user);
        } else {
            System.out.println("User with email " + user.getEmail() + " already exists.");
        }
    }

    @Override
    public void Supprimer(User user) {

    }

    @Override
    public void Supprimer(User user, int user_id) {

    }

    @Override
    public void Supprimer(int user_id) {
        try {
            String qry = "DELETE FROM `user` WHERE user_id=?";
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setInt(1,user_id);
            int value_supp = pst.executeUpdate();
            if (value_supp > 0) {
                System.out.println(" Suppression a ete effectuer avec sucess");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) {

    }

    @Override
    public void modifier(User user, int user_id) {

        try {
            String reqs = "UPDATE `user` SET nom=?,mdp=?,email=?,role=? WHERE user_id=?";
            PreparedStatement pst = cnx.prepareStatement(reqs);

            pst.setString(1, user.getNom());
            pst.setString(2, user.getMdp());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getRole());
            pst.setInt(5,user_id);


            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur Modifié !");
            } else {
                System.out.println("Erreur lors de la modification de l'utilisateur");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public ArrayList<User> getAll() {


        //retourner toute les perosnnes dans la bd
        //1- req SQL done
        //2 -execution de la req done
        // 3- remplire la liste done
        // 4 - retourner la liste done
        ArrayList<User> users = new ArrayList();
        String qry ="SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                User user = new User();
                user.setUser_id(rs.getInt(1));
                user.setNom(rs.getString("nom"));
                user.setMdp(rs.getString(3));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return users;
    }
    @Override
    public  Map<String, Integer> countUsersByRole() {
        Map<String, Integer> list = new HashMap<>();
        try {
            String qry = "select COUNT(*) as count, role from user GROUP BY role";
            Statement st = cnx.prepareStatement(qry);
            ResultSet rs = st.executeQuery(qry);
            while (rs.next()) {
                list.put(rs.getString("role"), rs.getInt("count"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    @Override
    public User FindByEmailAndPassword(String email, String mdp) {
        User user = new User();

        try {
            String qry = "SELECT * from user where email='" + email + "'AND mdp='" + mdp + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(qry);
            while (rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    @Override
    public String encrypt(String mdp) {

        return Base64.getEncoder().encodeToString(mdp.getBytes());
    }
    @Override
    public String decrypt(String mdp) {

        return new String(Base64.getMimeDecoder().decode(mdp));
    }
    @Override
    public void ban(User u) throws SQLException {

        String req = "UPDATE user SET is_blocked = 1 where User_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, u.getUser_id());
        ps.executeUpdate();

    }
    @Override

    public void unban(User u) throws SQLException {

        String req = "UPDATE user SET is_blocked = 0 where User_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, u.getUser_id());
        ps.executeUpdate();

    }



}






