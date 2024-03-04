package tn.esprit.esprit.services;

import tn.esprit.esprit.Securite.BCrypt;
import tn.esprit.esprit.interfaces.IUser;
import tn.esprit.esprit.models.User;
import tn.esprit.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceUser implements IUser<User> {
    Connection cnx;
    public static String email;

    public ServiceUser(){
        cnx= MyDb.getInstance().getCnx();
    }




    @Override
    public  void addUser(User user){
        String hashed = BCrypt.hashpw(user.getMdp(), BCrypt.gensalt());
        try {
            String qry = "INSERT INTO `user`( `nom`, `mdp`, `email`, `role` ,`tel`) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(qry);

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getMdp());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getTel());



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
    public boolean modifier(User user) {

        return false;
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
    public String login(String email, String mdp) {
        String message = "";
        String role = "";

        try {
            // Vérifiez si l'email et le mot de passe ne sont pas vides et non nuls
            if (email != null && !email.isEmpty() && mdp != null && !mdp.isEmpty()) {

                // Requête SQL pour récupérer les informations de l'utilisateur
                String query = "SELECT * FROM user WHERE email = ?";
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setString(1, email);
                ResultSet rs = pst.executeQuery();

                // Vérifiez si les informations d'identification sont valides
                if (rs.next() && BCrypt.checkpw(mdp, rs.getString("mdp"))) {
                    // Récupérez les informations de l'utilisateur
                    int userId = rs.getInt("user_id");
                    String nom = rs.getString("nom");
                    role = rs.getString("role");

                    // Affichez un message de bienvenue
                    System.out.println("Bonjour, " + nom + " !");

                } else {
                    System.err.println("Vérifiez vos identifiants !");
                    return "Vérifiez vos identifiants !";
                }
            } else {
                System.out.println("Champs vides");
                return "Veuillez fournir votre email et votre mot de passe.";
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            return "Une erreur est survenue lors de la connexion. Veuillez réessayer.";
        }

        // Si aucun rôle n'a été défini, il y a eu un problème
        if (role.isEmpty()) {
            message = "Impossible de récupérer le rôle de l'utilisateur.";
        }

        return role.isEmpty() ? message : role;
    }

    public int GetIDByEmail(String email) throws SQLException {
            String qry = "SELECT user_id FROM user WHERE email = ?";
            try (PreparedStatement statement = cnx.prepareStatement(qry)) {
                statement.setString(1, email);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("user_id");
                    }
                }
            }
            return -1; // Retourne une valeur par défaut si aucun utilisateur n'est trouvé

    }
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Extract user data from the result set
                    String username = rs.getString("nom");
                    String email = rs.getString("email");
                    // Create a new User object
                    User user = new User(userId, username, email);
                    return user;
                }
            }
        }
        // If no user found with the given ID, return null or throw an exception
        return null;
    }


    public void ModifMDP(String email, String mdp) throws SQLException{
        String hashed = BCrypt.hashpw(mdp, BCrypt.gensalt());
        String req = "UPDATE user SET mdp=? WHERE email=?";
        PreparedStatement stmt = cnx.prepareStatement(req);
        stmt.setString(1, hashed); // Utilise le mot de passe haché
        stmt.setString(2, email);
        stmt.executeUpdate();
    }






}






