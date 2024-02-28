package tn.esprit.IServices;

import tn.esprit.Models.Presence;
import tn.esprit.Models.Seance;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InServicePresence {
    void addPresence(Presence pr) throws SQLException;
    ArrayList<Presence> getAll();
     Presence update(Presence Pr, Date newDate, Seance newSeance)   ; //void delete(int cl) throws SQLException;
    //boolean deleteNom(classe cl) throws SQLException;




}
