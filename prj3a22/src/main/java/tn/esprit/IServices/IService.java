package tn.esprit.IServices;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IService <Personne>{
    void add (Personne personne);
    ArrayList<Personne> getAll();
    void update(Personne personne);
    boolean delete(Personne personne) throws SQLException;

}
