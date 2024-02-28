package tn.esprit.IServices;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IserviceC <classe>{

    void add (classe cl);
    ArrayList<classe> getAll();
    classe update(classe id);
    void delete(int cl) throws SQLException;
    boolean deleteNom(classe cl) throws SQLException;
    boolean deleteByName(String nomClasse);
    public classe getClasseByNom(String nomClasse);
    public ArrayList<String> getNomClasses();


}
