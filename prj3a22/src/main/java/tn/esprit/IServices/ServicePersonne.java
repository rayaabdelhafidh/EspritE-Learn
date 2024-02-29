package tn.esprit.IServices;

import tn.esprit.Models.EtatPresence;
import tn.esprit.Models.Personne;
import tn.esprit.Models.classe;
import tn.esprit.utilse.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePersonne implements IService<Personne> {
    final Connection cnx ;

    public ServicePersonne(){
        cnx =Database.getInstance().getCnx();

    }

    @Override
    public void add(Personne personne) {
        // ajouter une personne dans la bd
        //1 - req SQL done
        //2 - executer la req SQL done
        String qry ="INSERT INTO `Personne`(`age`, `nom`, `prenom`,`idClasse` ) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,personne.getAge());

            stm.setString(2,personne.getNom());
            stm.setString(3,personne.getPrenom());
            stm.setInt(4,personne.getIdClasse());


            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }


    }

    @Override
    public ArrayList<Personne> getAll() {


        //retourner toute les perosnnes dans la bd
        //1- req SQL done
        //2 -execution de la req done
        // 3- remplire la liste done
        // 4 - retourner la liste done
        ArrayList<Personne> personnes = new ArrayList();
        String qry ="SELECT * FROM `Personne`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Personne p = new Personne();
                p.setIdP(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString(3));
                p.setAge(rs.getInt("age"));

                personnes.add(p);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return personnes;
    }





    @Override
    public void update(Personne personne) {
        try {
            String req = "UPDATE Personne SET nom = ?, prenom = ? , age = ?  WHERE idP = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1,personne.getNom());
            pstmt.setString(2,personne.getPrenom());
            pstmt.setInt(3,personne.getAge());
            pstmt.executeUpdate();

            System.out.println("Personne " + personne.getIdP() + " Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    @Override
    public boolean delete(Personne personne)throws SQLException {
            try {
                String req = "DELETE FROM Personne  WHERE idP = ?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(1,personne.getIdP());
                pst.executeUpdate();
                System.out.println("Personne est supprimée");
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return false;
    }


    public ArrayList<Personne> getEtudiantsDeLaClasse(classe classe) {
        ArrayList<Personne> etudiants = new ArrayList<>();
        String query = "SELECT * FROM personne WHERE idClasse = ?";
        try {
         PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, classe.getidC());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Personne etudiant = new Personne();
                etudiant.getIdClasse(resultSet.getInt("idClasse"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setAge(resultSet.getInt("age"));
                // Vous devrez définir les autres attributs de l'étudiant en fonction de votre modèle

                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }


    public List<Personne> getEtudiantsParClasse(String nomClasse) {
        List<Personne> etudiants = new ArrayList<>();
        String query = "SELECT p.idPersonne, p.nom, p.prenom FROM Personne p " +
                "JOIN Classe c ON p.idClasse = c.idClasse " +
                "WHERE c.nomClasse = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setString(1, nomClasse);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idPersonne = rs.getInt("idPersonne");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                // Créer un objet Personne pour chaque enregistrement récupéré
                Personne etudiant = new Personne(idPersonne, nom, prenom);
                // Ajouter l'étudiant à la liste des étudiants
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }


    public void enregistrerEtatPresence(int idPersonne, EtatPresence etatPresence) {
        String query = "UPDATE Personne SET etat_presence = ? WHERE idPersonne = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setString(1, etatPresence.toString());
            pstmt.setInt(2, idPersonne);
            pstmt.executeUpdate();
            System.out.println("État de présence enregistré avec succès pour l'étudiant avec l'ID : " + idPersonne);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public EtatPresence getStatusOfStudent(int idPersonne) {
        EtatPresence etatPresence = null;
        String query = "SELECT etat_presence FROM Personne WHERE idPersonne = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idPersonne);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String etat = rs.getString("etat_presence");
                etatPresence = EtatPresence.valueOf(etat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etatPresence;
    }


    public void afficherPresenceEtat() {
        String query = "SELECT nom, prenom, etat_presence FROM Personne";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String etatPresence = rs.getString("etat_presence");

                System.out.println("Nom: " + nom + ", Prénom: " + prenom + ", État de présence: " + etatPresence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isAbsent(Personne personne) {
        String query = "SELECT etat_presence FROM Personne WHERE idPersonne = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, personne.getIdP());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String etatPresence = rs.getString("etat_presence");
                return "Absent".equals(etatPresence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPresent(Personne personne) {
        String query = "SELECT etat_presence FROM Personne WHERE idPersonne = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, personne.getIdP());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String etatPresence = rs.getString("etat_presence");
                return "Présent".equals(etatPresence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}







