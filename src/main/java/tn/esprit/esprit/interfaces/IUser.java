package tn.esprit.esprit.interfaces;

import tn.esprit.esprit.models.User;

import java.util.ArrayList;
import java.util.Map;

public interface IUser<U> {
    void addUser(User user);

    boolean EmailExiste(String email);

    void Supprimer(User user);

    void Supprimer(User user, int user_id);

    void Supprimer(int user_id);

    boolean modifier(User user);

    void modifier(User user, int id);

    ArrayList<User> getAll();

    Map<String, Integer> countUsersByRole();

    User FindByEmailAndPassword(String email, String mdp);







}


