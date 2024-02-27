package tn.esprit.esprite_learn.Services;
import tn.esprit.esprite_learn.IService.IService;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.utils.DataBase;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class ServiceEvenement implements IService<Evenement>{

    private Connection cnx ;
    public ServiceEvenement() throws SQLException {
        cnx =DataBase.getInstance().getConnexion();
    }
    @Override
    public void add(Evenement evenement) throws Exception {
        String sDateDebut = "31/12/1998";
        java.util.Date dateDebut = new SimpleDateFormat("dd/MM/yyyy").parse(sDateDebut);

        String qry ="INSERT INTO `evenement`(`NomEvenement`, `DateEvenement`, `LieuEvenement`, `PrixEvenement`, `AfficheEvenement`, `club`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,evenement.getNomEvenement());
            stm.setTimestamp(2, new Timestamp( evenement.getDateEvenement().getTime()));
            stm.setString(3,evenement.getLieuEvenement());
            stm.setInt(4,evenement.getPrixEvenement());
            stm.setString(5,evenement.getAfficheEvenement());
            stm.setInt(6,evenement.getClub());
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Evenement> display() {
        ArrayList<Evenement> evenement = new ArrayList();
        String qry ="SELECT * FROM `evenement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Evenement e = new Evenement();
                e.setIdEvenement(rs.getInt(1));
                e.setNomEvenement(rs.getString(2));
                e.setDateEvenement(rs.getDate(3));
                e.setLieuEvenement(rs.getString(4));
                e.setPrixEvenement(rs.getInt(5));
                e.setAfficheEvenement(rs.getString(6));
                e.setClub(rs.getInt(7));
                evenement.add(e);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return evenement;
    }

    @Override
    public void update(Evenement evenement) {
        String qry = "UPDATE club SET NomEvenement=?, DateEvenement=?, LieuEvenement=?, PrixEvenement=?, AfficheEvenement=?, club=? WHERE IdEvenement=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,evenement.getNomEvenement());
            stm.setTimestamp(2, new Timestamp( evenement.getDateEvenement().getTime()));
            stm.setString(3,evenement.getLieuEvenement());
            stm.setInt(4,evenement.getPrixEvenement());
            stm.setString(5,evenement.getAfficheEvenement());
            stm.setInt(6,evenement.getClub());
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void delete(Evenement evenement) {
        try {
            String qry="DELETE FROM evenement WHERE IdEvenement=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,evenement.getIdEvenement());
            stm.executeUpdate();
            System.out.println("evenement est supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Evenement find(String nom) {
        ArrayList<Evenement> evenements = new ArrayList();
        String qry = "SELECT `IdEvenement`, `NomEvenement`, `DateEvenement`, `LieuEvenement`, `PrixEvenement`, `AfficheEvenement`, `club` FROM `evenement` WHERE NomEvenement=?";
        Evenement c;
        try {
            PreparedStatement ps = cnx.prepareStatement(qry);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            c = new Evenement();
            while (rs.next()) {
                c.setIdEvenement(rs.getInt(1));
                c.setNomEvenement(rs.getString(2));
                c.setDateEvenement(rs.getDate(3));
                c.setLieuEvenement(rs.getString(4));
                c.setPrixEvenement(rs.getInt(5));
                c.setAfficheEvenement(rs.getString(6));
                c.setClub(rs.getInt(7));
                System.out.println("L'evenement cherche est : " + c.toString());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }
}
