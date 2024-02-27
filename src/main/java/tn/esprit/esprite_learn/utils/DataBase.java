package tn.esprit.esprite_learn.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBase {
    private static DataBase instance;
    private final String URL="jdbc:mysql://127.0.0.1:3306/esprite-learn";
    private final String USERNAME="root";
    private final String PASSWORD="";
    private Connection cnx;

    private DataBase ()throws SQLException{
        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("____not connected____ ");
        }
    }
    public static DataBase getInstance() throws SQLException {
        if(instance ==null)
            instance = new DataBase();
        return instance;
    }
    public Connection getConnexion(){
        return cnx;
    }
}
