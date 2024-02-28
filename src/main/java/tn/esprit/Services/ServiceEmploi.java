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
import java.util.ArrayList;
import java.util.*;

public class ServiceEmploi implements IServiceEmploi<emploi> {
    private Connection cnx ;
    public ServiceEmploi(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouterEmploi(emploi emploi) {
        String emploiQuery = "INSERT INTO emploi (premierDate, dernierDate, matiereId, salleId) VALUES (?, ?, ?, ?)";
        String jourSemaineQuery = "INSERT INTO emploi_jourSemaine (emploiId, jourSemaine) VALUES (?, ?)";
        String heureQuery = "INSERT INTO emploi_heure (emploiId, heure) VALUES (?, ?)";

        try {
            // Insertion into emploi
            PreparedStatement emploiStm = cnx.prepareStatement(emploiQuery, Statement.RETURN_GENERATED_KEYS);
            emploiStm.setDate(1, new java.sql.Date(emploi.getPremierDate().getTime()));
            emploiStm.setDate(2, new java.sql.Date(emploi.getDernierDate().getTime()));
            emploiStm.setInt(3, emploi.getMatiere().getIdM());
            emploiStm.setInt(4, emploi.getSalle().getSalleId());
            emploiStm.executeUpdate();

            // Get the auto-generated emploiId
            ResultSet generatedKeys = emploiStm.getGeneratedKeys();
            int emploiId = -1;
            if (generatedKeys.next()) {
                emploiId = generatedKeys.getInt(1);
            }

            // Insertion into emploi_jourSemaine
            PreparedStatement jourSemaineStm = cnx.prepareStatement(jourSemaineQuery);
            for (JourSemaine jourSemaine : emploi.getJourSemaine()) {
                jourSemaineStm.setInt(1, emploiId);
                jourSemaineStm.setString(2, jourSemaine.name());
                jourSemaineStm.executeUpdate();
            }

            // Insertion into emploi_heure
            PreparedStatement heureStm = cnx.prepareStatement(heureQuery);
            for (Heure heure : emploi.getHeure()) {
                heureStm.setInt(1, emploiId);
                heureStm.setString(2, heure.name());
                heureStm.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public ArrayList<emploi> getAll() {
        ArrayList<emploi> emplois = new ArrayList<>();
        String qry = "SELECT e.*, m.nomM, m.idM, s.salleId, s.numeroSalle, s.bloc, GROUP_CONCAT(ej.jourSemaine) AS joursSemaine, GROUP_CONCAT(eh.Heure) AS heures " +
                "FROM emploi e " +
                "JOIN matiere m ON e.matiereId = m.idM " +
                "LEFT JOIN emploi_jourSemaine ej ON e.emploiId = ej.emploiId " +
                "LEFT JOIN emploi_heure eh ON e.emploiId = eh.emploiId " +
                "LEFT JOIN salle s ON e.salleId = s.salleId " +
                "GROUP BY e.emploiId";


        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                emploi e = new emploi();
                e.setEmploiId(rs.getInt("emploiId"));
                e.setPremierDate(rs.getDate("premierDate"));
                e.setDernierDate(rs.getDate("dernierDate"));

                // Fetch the name of the matiere based on its ID
                ServiceMatiere matiereService = new ServiceMatiere();
                int matiereId = rs.getInt("idM");

// Assuming you have a method to get Matiere by ID
                Matiere matiere = matiereService.getById(matiereId);
                e.setMatiere(matiere);

                Salle salle = new Salle();
                salle.setSalleId(rs.getInt("salleId"));
                salle.setNumeroSalle(rs.getInt("numeroSalle"));
                salle.setBloc(Bloc.valueOf(rs.getString("bloc")));
                e.setSalle(salle);

                // Splitting the concatenated strings into arrays of enum values
                String[] jourSemaineArray = rs.getString("joursSemaine").split(",");
                String[] heureArray = rs.getString("heures").split(",");

                Set<JourSemaine> jourSemaineSet = new HashSet<>();
                for (String jour : jourSemaineArray) {
                    jourSemaineSet.add(JourSemaine.valueOf(jour));
                }
                e.setJourSemaine(jourSemaineSet);

                Set<Heure> heureSet = new HashSet<>();
                for (String heure : heureArray) {
                    heureSet.add(Heure.valueOf(heure));
                }
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
        try {
            String emploiQuery = "UPDATE `emploi` SET `premierDate`=?, `dernierDate`=?, `matiereId`=?, `salleId`=? WHERE `emploiId`=?";
            String jourSemaineQuery = "INSERT INTO `emploi_jourSemaine` (`emploiId`, `jourSemaine`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `jourSemaine` = VALUES(`jourSemaine`)";
            String heureQuery = "INSERT INTO `emploi_heure` (`emploiId`, `heure`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `heure` = VALUES(`heure`)";

            // Update emploi table
            PreparedStatement emploiStm = cnx.prepareStatement(emploiQuery);
            emploiStm.setDate(1, new java.sql.Date(emploi.getPremierDate().getTime()));
            emploiStm.setDate(2, new java.sql.Date(emploi.getDernierDate().getTime()));
            emploiStm.setInt(3, emploi.getMatiere().getIdM());
            emploiStm.setInt(4, emploi.getSalle().getSalleId());
            emploiStm.setInt(5, emploi.getEmploiId());
            int emploiRowsUpdated = emploiStm.executeUpdate();

            // Update emploi_jourSemaine table
            PreparedStatement jourSemaineStm = cnx.prepareStatement(jourSemaineQuery);
            for (JourSemaine jourSemaine : emploi.getJourSemaine()) {
                jourSemaineStm.setInt(1, emploi.getEmploiId());
                jourSemaineStm.setString(2, jourSemaine.name());
                jourSemaineStm.executeUpdate();
            }

            // Update emploi_heure table
            PreparedStatement heureStm = cnx.prepareStatement(heureQuery);
            for (Heure heure : emploi.getHeure()) {
                heureStm.setInt(1, emploi.getEmploiId());
                heureStm.setString(2, heure.name());
                heureStm.executeUpdate();
            }

            System.out.println("emploi " + emploi.getEmploiId() + " Modifiée !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean supprimerEmploi(emploi emploi) {
        String qry = "DELETE FROM `emploi` WHERE `emploiId`=?";
        String jourSemaineQuery = "DELETE FROM `emploi_jourSemaine` WHERE `emploiId`=?";
        String heureQuery = "DELETE FROM `emploi_heure` WHERE `emploiId`=?";
        try {
            //delete from emploi_jourSemaine
            PreparedStatement jourSemaineStm = cnx.prepareStatement(jourSemaineQuery);
            jourSemaineStm.setInt(1, emploi.getEmploiId());
            jourSemaineStm.executeUpdate();
            //delete from eploi_heure
            PreparedStatement heureStm = cnx.prepareStatement(heureQuery);
            heureStm.setInt(1, emploi.getEmploiId());
            heureStm.executeUpdate();
            //delete from emploi
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, emploi.getEmploiId());
            int emploisSupprimés = stm.executeUpdate();
            return emploisSupprimés > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public emploi getById(int emploiId) {
        emploi e = null;

        String query = "SELECT e.*, m.nomM, m.idM, s.salleId, s.numeroSalle, s.bloc, GROUP_CONCAT(ej.jourSemaine) AS joursSemaine, GROUP_CONCAT(eh.Heure) AS heures " +
                "FROM emploi e " +
                "JOIN matiere m ON e.matiereId = m.idM " +
                "LEFT JOIN emploi_jourSemaine ej ON e.emploiId = ej.emploiId " +
                "LEFT JOIN emploi_heure eh ON e.emploiId = eh.emploiId " +
                "LEFT JOIN salle s ON e.salleId = s.salleId " +
                "WHERE e.emploiId = ?";

        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, emploiId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                e = new emploi();
                e.setEmploiId(rs.getInt("emploiId"));
                e.setPremierDate(rs.getDate("premierDate"));
                e.setDernierDate(rs.getDate("dernierDate"));

                // Fetch Matiere
                Matiere matiere = new Matiere();
                matiere.setIdM(rs.getInt("idM"));
                matiere.setNomM(rs.getString("nomM"));
                e.setMatiere(matiere);

                // Fetch Salle
                Salle salle = new Salle();
                salle.setSalleId(rs.getInt("salleId"));
                salle.setNumeroSalle(rs.getInt("numeroSalle"));
                salle.setBloc(Bloc.valueOf(rs.getString("bloc")));
                e.setSalle(salle);

                // Splitting the concatenated strings into arrays of enum values
                String[] jourSemaineArray = rs.getString("joursSemaine").split(",");
                String[] heureArray = rs.getString("heures").split(",");

                Set<JourSemaine> jourSemaineSet = new HashSet<>();
                for (String jour : jourSemaineArray) {
                    jourSemaineSet.add(JourSemaine.valueOf(jour));
                }
                e.setJourSemaine(jourSemaineSet);

                Set<Heure> heureSet = new HashSet<>();
                for (String heure : heureArray) {
                    heureSet.add(Heure.valueOf(heure));
                }
                e.setHeure(heureSet);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }


}
