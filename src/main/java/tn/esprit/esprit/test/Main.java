package tn.esprit.esprit.test;

import tn.esprit.esprit.models.User;
import tn.esprit.esprit.services.ServiceUser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParseException, SQLException {
        ServiceUser su = new ServiceUser();

        //*add
        //User u1 = new User("hamma","hamma123","hamma@gmail.com","etudiant",true);
        //User u2 = new User(1,"khalil","khalil123","khalil@esprit.tn","etudiant");

        //su.addUser(u1);
        //su.addUser(u2);
        //System.out.println(u1);


        //*register
        //su.registerUser(new User("hamma","hamma123","hamma@gmail.com","etudiant",true));

        //*supprimer
        //su.Supprimer(2);


        //*modifier
        //su.modifier(new User("ahmed","ahmed123","ahmed@mail.tn","admin"));
        //User user_modified = new User("ahmed","ahmed123","ahmed@mail.tn","admin");
        //su.modifier(user_modified,1);

        //*
        //System.out.println(su.getAll());

        //*countUsersByRole()
        //Map<String, Integer> userCountByRole = su.countUsersByRole();
        //System.out.println("Nombre d'utilisateurs par rôle :");
        //for (Map.Entry<String, Integer> entry : userCountByRole.entrySet()) {
        //System.out.println(entry.getKey() + ": " + entry.getValue());}


        //*FindByEmailAndPassword
        //System.out.println(su.FindByEmailAndPassword("khalil@esprit.tn","khalil123"));

        //*encrypt:decrypt
        //System.out.println(su.encrypt("khalil123"));
        //System.out.println(su.decrypt("a2hhbGlsMTIz"));

        su.login("melek@gmail.com","123456789");



    }
}
