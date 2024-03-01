package tn.esprit.utilse;
import tn.esprit.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private final String URL="jdbc:mysql://127.0.0.1:3306/workshop3a22";
    private final String USERNAME="root";
    private final String PASSWORD="";
    private Connection cnx;


    public Database(){
        try {
            cnx= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
       catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("----- not connected");
       }
    }
    public static Database getInstance(){
        if(instance ==null) //lena idha ma3andouch instance yasna3o sinn yraj3o lel instance cree

            instance = new Database();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
