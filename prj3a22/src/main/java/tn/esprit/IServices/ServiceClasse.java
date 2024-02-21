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
    public ArrayList<classe> getAll() {
        ArrayList<classe> classees = new ArrayList<>();
        String qry = "SELECT * FROM `classe`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                classe C = new classe();
                C.setidC(rs.getInt("idC"));
                C.setNomClasse(rs.getString("nomClasse"));
                C.setFiliere(filiere.valueOf(rs.getString("filiere")));
                C.setNbreEtud(rs.getInt("nbreEtudi"));
                String niveauStr = rs.getString("niveaux");
                if (niveauStr != null && !niveauStr.isEmpty()) {
                    C.setNiveaux(valueOf(niveauStr));
                } else {
                    // Traiter le cas où le niveau est vide ou null
                    // Par exemple, vous pouvez affecter une valeur par défaut
                    C.setNiveaux(niveaux._1A);
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
            String req = "UPDATE classe SET nomClasse = ?, filiere = ?, nbreEtudi = ?, niveaux = ? WHERE idC = ?";
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
        String sql = "DELETE FROM classe  WHERE idC = ?";
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

        String req = "SELECT nomClasse, filiere, nbreEtudi, niveaux FROM classe WHERE idC = ?";
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
}