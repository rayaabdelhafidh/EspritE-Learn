package tn.esprit.IServices;

import tn.esprit.Models.*;
import tn.esprit.utilse.Database;

import java.sql.*;
import java.util.ArrayList;

import static tn.esprit.Models.niveaux.valueOf;

public class ServicePresence implements InServicePresence{

    final Connection cnx ;
    public ServicePresence(){
        cnx = Database.getInstance().getCnx();

    }
    @Override
    public void addPresence(Presence pr) throws SQLException, IllegalArgumentException {
        // Vérifier si le nom de classe fourni correspond à une classe existante dans la base de données
        String qryCheckClasse = "SELECT idClasse FROM classe WHERE nomClasse = ?";
        try (PreparedStatement stmCheckClasse = cnx.prepareStatement(qryCheckClasse)) {
            stmCheckClasse.setString(1, pr.getNomClasse());
            ResultSet rs = stmCheckClasse.executeQuery();
            if (!rs.next()) {
                throw new IllegalArgumentException("Le nom de classe fourni n'existe pas dans la base de données !");
            } else {
                int idClasse = rs.getInt("idClasse");
                String nomClasse = pr.getNomClasse();

                // Insérer la présence dans la base de données avec l'ID de la classe et le nom de la classe
                String qryPresence = "INSERT INTO `presence`(`etat`, `date`, `seance`, `idClasse`, `nomClasse`) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmPresence = cnx.prepareStatement(qryPresence)) {
                    stmPresence.setString(1, pr.getEtat().name());
                    java.util.Date utilDate = pr.getDate();
                    Date sqlDate = new Date(utilDate.getTime());
                    stmPresence.setDate(2, sqlDate);
                    stmPresence.setString(3, pr.getSeance().name());
                    stmPresence.setInt(4, idClasse);
                    stmPresence.setString(5, nomClasse);

                    stmPresence.executeUpdate();
                    System.out.println("Nouvelle présence ajoutée avec succès !");
                } catch (SQLException e) {
                    System.out.println("Erreur lors de l'ajout de la présence : " + e.getMessage());
                }
            }
        }
    }





    @Override
    public ArrayList<Presence> getAll() {

        ArrayList<Presence> presencess = new ArrayList<>();
        String qry = "SELECT * FROM `presence`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next())
            {
                Presence P = new Presence();
                P.setIdPresence(rs.getInt("idPresence"));
                P.setEtatPresence(EtatPresence.valueOf(rs.getString("etat")));
                P.setDate(rs.getDate("date"));
                P.setSeance(Seance.valueOf(rs.getString("seance")));
              P.setNomClasse(rs.getString("nomClasse"));
              presencess.add(P);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return presencess;
       // return null;
    }

    @Override
    public Presence update(Presence Pr, EtatPresence EtP) {

        try {
            String req = "UPDATE presence SET etat = ? WHERE idPresence = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, String.valueOf(Pr.getEtat()));
            pstmt.setInt(2, Pr.getIdPresence()); // Set the id for the WHERE clause

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Classe " + Pr.getIdPresence() + " Modifiée !");
            } else {
                System.out.println("Aucune classe modifiée !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la classe : " + e.getMessage());
        }

        return Pr;



        //return null;
    }
}
