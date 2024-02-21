package tn.esprit.Services;

import tn.esprit.Interfaces.IServiceEmploi;
import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Enum.Heure;
import tn.esprit.Models.Enum.JourSemaine;
import tn.esprit.Models.Matiere;
import tn.esprit.Models.Salle;
import tn.esprit.Models.emploi;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

public class ServiceEmploi implements IServiceEmploi<emploi> {
    private Connection cnx ;
    public ServiceEmploi(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouterEmploi(emploi emploi) {
        String emploiQuery = "INSERT INTO `emploi`(`premierDate`, `dernierDate`, `matiereId`, `salleId`) VALUES (?,?,?,?)";
        String jourSemaineQuery = "INSERT INTO `emploi_jourSemaine`(`emploiId`, `jourSemaine`) VALUES (?, ?)";
        String heureQuery = "INSERT INTO `emploi_heure`(`emploiId`, `heure`) VALUES (?, ?)";

        try {
            // insertion emploi
            PreparedStatement emploiStm = cnx.prepareStatement(emploiQuery, Statement.RETURN_GENERATED_KEYS);
            emploiStm.setDate(1, new Date(emploi.getPremierDate().getTime()));
            emploiStm.setDate(2, new Date(emploi.getDernierDate().getTime()));
            emploiStm.setInt(3, emploi.getMatiere().getMatiereId());
            emploiStm.setLong(4, emploi.getSalle().getSalleId());
            emploiStm.executeUpdate();

            // Get the auto-generated emploiId
            ResultSet generatedKeys = emploiStm.getGeneratedKeys();
            if (generatedKeys.next()) {
                int emploiId = generatedKeys.getInt(1);

                // insertion jourSemaine
                PreparedStatement jourSemaineStm = cnx.prepareStatement(jourSemaineQuery);
                for (JourSemaine jourSemaine : emploi.getJourSemaine()) {
                    jourSemaineStm.setInt(1, emploiId);
                    jourSemaineStm.setString(2, jourSemaine.name());
                    jourSemaineStm.addBatch();
                }
                jourSemaineStm.executeBatch();

                // insertion heure
                PreparedStatement heureStm = cnx.prepareStatement(heureQuery);
                for (Heure heure : emploi.getHeure()) {
                    heureStm.setInt(1, emploiId);
                    heureStm.setString(2, heure.name());
                    heureStm.addBatch();
                }
                heureStm.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   @Override
    public ArrayList<emploi> getAll() {
       ArrayList<emploi> emplois = new ArrayList<>();
        String qry = "SELECT * FROM `emploi`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                emploi e = new emploi();
                e.setEmploiId(rs.getInt("emploiId"));
                int matiereId = rs.getInt("matiereId");
                Matiere matiere = new Matiere();
                matiere.setMatiereId(matiereId);
                e.setMatiere(matiere);
                int salleId = rs.getInt("salleId");
                Salle salle = new Salle();
                salle.setSalleId(salleId);
                e.setSalle(salle);
//                Set<JourSemaine> jourSemaineSet = getJourSemaineByEmploiId(e.getEmploiId()); // You need to implement this method
//                e.setJourSemaine(jourSemaineSet);
                Set<JourSemaine> jourSemaineSet = new HashSet<>();
                e.setJourSemaine(jourSemaineSet);
                Set<Heure> heureSet = new HashSet<>();
                e.setHeure(heureSet);
                emplois.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return emplois;
   }
    @Override
    public void modifierEmploi(emploi emploi) {

    }

    @Override
    public boolean supprimerEmploi(emploi emploi) {
        return false;
    }

    @Override
    public emploi getById(int Id) {
        return null;
    }
}
