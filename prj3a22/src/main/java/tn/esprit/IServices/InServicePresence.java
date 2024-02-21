package tn.esprit.IServices;

import tn.esprit.Models.EtatPresence;
import tn.esprit.Models.Presence;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InServicePresence {
    void addPresence(Presence pr) throws SQLException;
    ArrayList<Presence> getAll();
    Presence update(Presence id , EtatPresence EtP);
    //void delete(int cl) throws SQLException;
    //boolean deleteNom(classe cl) throws SQLException;




}
