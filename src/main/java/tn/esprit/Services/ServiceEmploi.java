package tn.esprit.Services;

import tn.esprit.Interfaces.IServiceEmploi;
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
        String emploiQuery = "INSERT INTO `emploi`(`premierDate`, `dernierDate`, `matiereId`, `salleId`) VALUES (?,?,?,?)";
        String jourSemaineQuery = "INSERT INTO `emploi_jourSemaine`(`emploiId`, `jourSemaine`) VALUES (?, ?)";
        String heureQuery = "INSERT INTO `emploi_heure`(`emploiId`, `heure`) VALUES (?, ?)";

        try {
            // insertion emploi
            PreparedStatement emploiStm = cnx.prepareStatement(emploiQuery, Statement.RETURN_GENERATED_KEYS);
            emploiStm.setDate(1, new java.sql.Date(emploi.getPremierDate().getTime()));
            emploiStm.setDate(2, new java.sql.Date(emploi.getDernierDate().getTime()));
            emploiStm.setInt(3, emploi.getMatiere().getMatiereId());
            emploiStm.setInt(4, emploi.getSalle().getSalleId());
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
        String qry = "SELECT e.*, m.nomMatiere, GROUP_CONCAT(ej.jourSemaine) AS joursSemaine, GROUP_CONCAT(eh.Heure) AS heures " +
                "FROM emploi e " +
                "JOIN emploi_jourSemaine ej ON e.emploiId = ej.emploiId " +
                "JOIN emploi_heure eh ON e.emploiId = eh.emploiId " +
                "JOIN matiere m ON e.matiereId = m.matiereId " +
                "GROUP BY e.emploiId";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                emploi e = new emploi();
                e.setEmploiId(rs.getInt("emploiId"));
                e.setPremierDate(rs.getDate("premierDate"));
                e.setDernierDate(rs.getDate("dernierDate"));
//                int matiereId = rs.getInt("matiereId");
//                Matiere matiere = new Matiere();
//                matiere.setMatiereId(matiereId);
//                e.setMatiere(matiere);
                // Assuming Matiere has a constructor that accepts matiereId
                String nomMatiere = rs.getString("nomMatiere");
                Matiere matiere = new Matiere(nomMatiere);
                e.setMatiere(matiere);

                int salleId = rs.getInt("salleId");
                Salle salle = new Salle();
                salle.setSalleId(salleId);
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

    // `matiereId`, `salleId`
    @Override
    public void modifierEmploi(emploi emploi) {
        try {
            String qry = "UPDATE `emploi` SET `premierDate`=?, `dernierDate`=?, `matiereId`=?, `salleId`=? WHERE `emploiId`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setDate(1, new java.sql.Date(emploi.getPremierDate().getTime()));
            stm.setDate(2, new java.sql.Date(emploi.getDernierDate().getTime()));
            stm.setInt(3, emploi.getMatiere().getMatiereId());
            stm.setInt(4, emploi.getSalle().getSalleId());
            stm.setInt(5, emploi.getEmploiId());
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("emploi " + emploi.getEmploiId() + " Modifiée !");
            } else {
                System.out.println("Aucun emploi modifiée !");
           }
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
        String query = "SELECT e.*, ej.jourSemaine, eh.Heure " +
                "FROM emploi e " +
                "JOIN emploi_jourSemaine ej ON e.emploiId = ej.emploiId " +
                "JOIN emploi_heure eh ON e.emploiId = eh.emploiId " +
                "WHERE e.emploiId = ?";

        try {
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setInt(1, emploiId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                e = new emploi();
                e.setEmploiId(rs.getInt("emploiId"));
                e.setPremierDate(rs.getDate("premierDate"));
                e.setDernierDate(rs.getDate("dernierDate"));
                int matiereId = rs.getInt("matiereId");
                Matiere matiere = new Matiere();
                matiere.setMatiereId(matiereId);
                e.setMatiere(matiere);
                int salleId = rs.getInt("salleId");
                Salle salle = new Salle();
                salle.setSalleId(salleId);
                Set<JourSemaine> jourSemaineSet = new HashSet<>();
                JourSemaine jourSemaine = JourSemaine.valueOf(rs.getString("jourSemaine"));
                jourSemaineSet.add(jourSemaine);
                e.setJourSemaine(jourSemaineSet);
                Set<Heure> heureSet = new HashSet<>();
                Heure heure = Heure.valueOf(rs.getString("Heure"));
                heureSet.add(heure);
                e.setHeure(heureSet);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }


}
