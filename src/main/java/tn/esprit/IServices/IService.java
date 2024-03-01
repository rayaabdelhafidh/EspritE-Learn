package tn.esprit.IServices;
import tn.esprit.Models.Personne;

import java.sql.SQLException;
import java.util.ArrayList;
import tn.esprit.IServices.ServiceClasse;

public interface IService <Personne>{
    void add (Personne personne);
    ArrayList<Personne> getAll();
    void update(Personne personne);
    boolean delete(Personne personne) throws SQLException;
     ArrayList<Personne> getEtudiantsAvecPresence(String nomClasse);

}
