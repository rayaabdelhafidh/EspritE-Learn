package tn.esprit.IServices;

import tn.esprit.Models.EtatPresence;
import tn.esprit.Models.Presence;
import tn.esprit.utilse.Database;

import java.sql.*;
import java.util.ArrayList;

public class ServicePresence implements InServicePresence{

    final Connection cnx ;
    public ServicePresence(){
        cnx = Database.getInstance().getCnx();

    }
    @Override
    public void addPresence(Presence pr) throws SQLException ,IllegalArgumentException{

        // Vérifier si le nom de classe fourni correspond à une classe existante dans la base de données
       /* String qryCheckClasse = "SELECT COUNT(*) FROM classe WHERE nomClasse = ?";
        try (PreparedStatement stmCheckClasse = cnx.prepareStatement(qryCheckClasse)) {
            stmCheckClasse.setString(1, pr.getNomClasse());
            ResultSet rs = stmCheckClasse.executeQuery();
            if (!rs.next() || rs.getInt(1) == 0) {
                throw new IllegalArgumentException("Le nom de classe fourni n'existe pas dans la base de données !");
            }
        }*/

        // Si la vérification est réussie, insérer la présence dans la base de données
        String qryPresence = "INSERT INTO `presence`(`etat`, `date`, `seance`, `nomClasse`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmPresence = cnx.prepareStatement(qryPresence)) {
            stmPresence.setString(1, pr.getEtat().name());
            stmPresence.setDate(2, (Date) pr.getDate());
            stmPresence.setString(3, pr.getSeance().name());
            stmPresence.setString(4, pr.getNomClasse());

            // Exécuter la requête d'insertion de la présence
            stmPresence.executeUpdate();

            System.out.println("Nouvelle présence ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la présence : " + e.getMessage());
        }
    }





    @Override
    public ArrayList<Presence> getAll() {
        return null;
    }

    @Override
    public Presence update(Presence id, EtatPresence EtP) {
        return null;
    }
}
