package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    final String URL="jdbc:mysql://127.0.0.1:3306/espritlearn"; //localhost=127.0.0.1  3306:port mysql
    final String USER="root";
    final String PWD="";
    private Connection cnx;
    private static MyDataBase instance;  //ay variable f wost methode static lazmha tkoun static

    //////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    private MyDataBase()  {  //7atyneh private bch na3amlouch plusieurs instance:singleuton une seule instance
        try {
            //DriverManager est une classe de l'API JDBC (Java Database Connectivity) de Java. Son rôle principal est de gérer une liste de pilotes de base de données disponibles.
            //Chaque base de données a son propre pilote JDBC spécifique qui  permet à Java de communiquer avec cette base de données particulière
            cnx= DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connecter");
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    public static MyDataBase getInstance(){ //singleton:une seule instance
        if(instance==null){
            instance=new MyDataBase();
        }
        else{
            System.out.println("Deja connecter");
        }
        return instance;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////



    public Connection getCnx(){
        return cnx;
    }

}
