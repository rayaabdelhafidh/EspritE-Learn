package tn.esprit.esprit.interfaces;

import tn.esprit.esprit.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface IUser<U> {
    void addUser(User user);

    boolean EmailExiste(String email);

    void registerUser(User user);

    void Supprimer(User user);

    void Supprimer(User user, int user_id);

    void Supprimer(int user_id);

    void modifier(User user);

    void modifier(User user, int id);

    ArrayList<User> getAll();

    Map<String, Integer> countUsersByRole();

    User FindByEmailAndPassword(String email, String mdp);

    String encrypt(String mdp);

    String decrypt(String mdp);

    void ban(User u) throws SQLException;

    void unban(User u) throws SQLException;



}


