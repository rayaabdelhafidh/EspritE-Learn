package tn.esprit.IServices;

import tn.esprit.Models.Personne;
import tn.esprit.utilse.Database;

import java.sql.*;
import java.util.ArrayList;

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
        String qry ="INSERT INTO `Personne`(`nom`, `prenom`, `age`) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,personne.getNom());
            stm.setString(2,personne.getPrenom());
            stm.setInt(3,personne.getAge());

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
}