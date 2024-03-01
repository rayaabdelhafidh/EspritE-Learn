package tn.esprit.IServices;

import tn.esprit.Models.classe;
import tn.esprit.Models.filiere;
import tn.esprit.Models.niveaux;
import tn.esprit.utilse.Database;

import java.sql.*;
import java.util.ArrayList;

import static tn.esprit.Models.niveaux.valueOf;

public class ServiceClasse implements IserviceC <classe> {
    final Connection cnx ;
    public ServiceClasse(){
        cnx =Database.getInstance().getCnx();

    }

    @Override
    public void add(classe cl) {
        // ajouter une personne dans la bd
        //1 - req SQL done
        //2 - executer la req SQL done
        String qry ="INSERT INTO `classe`(`nomClasse`, `filiere`, `nbreEtudi`,`niveaux`) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,cl.getNomClasse());
            stm.setString(2,cl.getFiliere().name());
            stm.setInt(3,cl.getNbreEtud());

            stm.setString(4, cl.getNiveaux().name());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }


    }

    @Override
    public ArrayList<classe> getAll()
    {
        ArrayList<classe> classees = new ArrayList<>();
        String qry = "SELECT * FROM `classe`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                classe C = new classe();
                C.setidC(rs.getInt("idClasse")); // Affichage de l'ID
                C.setNomClasse(rs.getString("nomClasse"));
                C.setFiliere(filiere.valueOf(rs.getString("filiere")));
                C.setNbreEtud(rs.getInt("nbreEtudi"));
                String niveauStr = rs.getString("niveaux");
                if (niveauStr != null && !niveauStr.isEmpty()) {
                    C.setNiveaux(valueOf(niveauStr));
                } else {
                    C.setNiveaux(niveaux._2A);
                }
                classees.add(C);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classees;
    }






    @Override
    public classe update(classe cl) {
        try {
            String req = "UPDATE classe SET nomClasse = ?, filiere = ?, nbreEtudi = ?, niveaux = ? WHERE idClasse = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, cl.getNomClasse());
            pstmt.setString(2, cl.getFiliere().name());
            pstmt.setInt(3, cl.getNbreEtud());
            pstmt.setString(4, cl.getNiveaux().name());
            pstmt.setInt(5, cl.getidC()); // Set the id for the WHERE clause

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Classe " + cl.getidC() + " Modifiée !");
            } else {
                System.out.println("Aucune classe modifiée !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la classe : " + e.getMessage());
        }

        return cl;
    }

    @Override
    public void delete(int cl)throws SQLException {
        String sql = "DELETE FROM classe  WHERE idClasse = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, cl);
            pstmt.executeUpdate();
            System.out.println("classe supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public classe getClasse(int id) {
        classe C = new classe();

        String req = "SELECT nomClasse, filiere, nbreEtudi, niveaux FROM classe WHERE idClasse = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    C.setidC(id);
                    C.setNomClasse(rs.getString("nomClasse"));
                    C.setFiliere(filiere.valueOf(rs.getString("filiere")));
                    C.setNbreEtud(rs.getInt("nbreEtudi"));
                    try {
                        C.setNiveaux(niveaux.valueOf(rs.getString("niveaux")));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erreur: Niveau invalide pour la classe " + id);
                        return null; // Ou lancez une exception appropriée
                    }
                  //  return C;
                } else {
                    System.err.println("Erreur: Aucune classe trouvée avec l'ID " + id);
                    return null; // Ou lancez une exception appropriée
                }
                return C;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la classe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }




    @Override
    public boolean deleteNom(classe cl) throws SQLException {
        try {
            String req = "DELETE FROM classe  WHERE nomClasse = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1,cl.getNomClasse());
            pst.executeUpdate();
            System.out.println("Classe est supprimée");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByName(String nomClasse) {
        try {
            String req = "DELETE FROM classe WHERE nomClasse = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, nomClasse);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Classe " + nomClasse + " supprimée avec succès !");
                return true;
            } else {
                System.out.println("Aucune classe n'a été supprimée (nom de classe introuvable) !");
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la classe: " + ex.getMessage());
            return false;
        }
    }

    public classe getClasseByNom(String nomClasse) {
        classe C = new classe();

        String req = "SELECT idClasse, filiere, nbreEtudi, niveaux FROM classe WHERE nomClasse = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, nomClasse);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    C.setidC(rs.getInt("idClasse"));
                    C.setNomClasse(nomClasse);
                    C.setFiliere(filiere.valueOf(rs.getString("filiere")));
                    C.setNbreEtud(rs.getInt("nbreEtudi"));
                    try {
                        C.setNiveaux(niveaux.valueOf(rs.getString("niveaux")));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erreur: Niveau invalide pour la classe " + nomClasse);
                        return null;
                    }
                } else {
                    System.err.println("Erreur: Aucune classe trouvée avec le nom " + nomClasse);
                    return null;
                }
                return C;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la classe: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getNomClasses() {
        ArrayList<String> nomClasses = new ArrayList<>();
        String query = "SELECT nomClasse FROM classe";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String nomClasse = resultSet.getString("nomClasse");
                nomClasses.add(nomClasse);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des noms de classe : " + e.getMessage());
            // Vous pouvez choisir de lever une exception ou de gérer l'erreur d'une autre manière
        }
        return nomClasses;
    }




}