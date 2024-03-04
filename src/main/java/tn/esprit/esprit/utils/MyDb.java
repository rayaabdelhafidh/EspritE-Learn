package tn.esprit.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private static MyDb instance ;
    private final String URL ="jdbc:mysql://127.0.0.1:3306/espritlearn";
    private final String USERNAME="root";
    private final String PASSWORD ="";

    Connection cnx ;


    private MyDb(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("____not connected____ ");

        }

    }
    public static MyDb getInstance(){
        if (instance == null)
            instance = new MyDb();

        return instance;
    }
    public Connection getCnx(){
        return cnx;
    }

}


