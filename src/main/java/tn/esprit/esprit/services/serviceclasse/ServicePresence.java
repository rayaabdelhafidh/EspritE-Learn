package tn.esprit.esprit.services.serviceclasse;



import tn.esprit.esprit.iservice.servicesclasse.InServicePresence;
import tn.esprit.esprit.models.modelsclasse.Presence;
import tn.esprit.esprit.models.modelsclasse.Seance;
import tn.esprit.esprit.utils.MyDb;

import java.sql.*;
import java.util.ArrayList;

public class ServicePresence implements InServicePresence {

    final Connection cnx;

    public ServicePresence() {
        cnx = MyDb.getInstance().getCnx();
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
                String qryPresence = "INSERT INTO `presence`(`date`, `seance`, `idClasse`, `nomClasse`) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmPresence = cnx.prepareStatement(qryPresence)) {
                    java.util.Date utilDate = pr.getDate();
                    Date sqlDate = new Date(utilDate.getTime());
                    stmPresence.setDate(1, sqlDate);
                    stmPresence.setString(2, pr.getSeance().name());
                    stmPresence.setInt(3, idClasse);
                    stmPresence.setString(4, nomClasse);

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
            while (rs.next()) {
                Presence P = new Presence();
                P.setIdPresence(rs.getInt("idPresence"));
                P.setDate(rs.getDate("date"));
                P.setSeance(Seance.valueOf(rs.getString("seance")));
                P.setIdClasse(rs.getInt("idClasse"));
                P.setNomClasse(rs.getString("nomClasse"));
                presencess.add(P);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return presencess;
    }
    @Override
    public ArrayList<Presence> getAllwithouId() {
        ArrayList<Presence> presencess = new ArrayList<>();
        String qry = "SELECT * FROM `presence`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Presence P = new Presence();
                P.setDate(rs.getDate("date"));
                P.setSeance(Seance.valueOf(rs.getString("seance")));
                P.setNomClasse(rs.getString("nomClasse"));
                presencess.add(P);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return presencess;
    }


    @Override
    public Presence update(Presence Pr, Date newDate, Seance newSeance) {
        try {
            String req = "UPDATE presence SET  date = ?, seance = ? WHERE idPresence = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
          //  pstmt.setString(1, EtP.name());
            pstmt.setDate(1, new Date(newDate.getTime())); // Convertir la date en java.sql.Date
            pstmt.setString(2, newSeance.name());
            pstmt.setInt(3, Pr.getIdPresence()); // Set the id for the WHERE clause

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Présence " + Pr.getIdPresence() + " modifiée !");
                Pr.setDate(newDate); // Mettre à jour la date dans l'objet Presence
                Pr.setSeance(newSeance); // Mettre à jour la séance dans l'objet Presence
            } else {
                System.out.println("Aucune présence modifiée !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la présence : " + e.getMessage());
        }

        return Pr;
    }


    public boolean etudiantPossedePresence(int idEtudiant, Date date, Seance seance) {
        String query = "SELECT COUNT(*) FROM etudiant_presence " +
                "JOIN presence ON etudiant_presence.id_presence = presence.idPresence " +
                "WHERE etudiant_presence.id_etudiant = ? AND presence.date = ? AND presence.seance = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEtudiant);
            pstmt.setDate(2, new Date(date.getTime()));
            pstmt.setString(3, seance.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}

