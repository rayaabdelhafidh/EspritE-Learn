package tn.esprit.Services;

import tn.esprit.IService.IService;
import tn.esprit.Models.Clubs;
import tn.esprit.utils.DataBase;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ServiceClub implements IService<Clubs> {

    private Connection cnx ;
    public ServiceClub() throws SQLException {
        cnx =DataBase.getInstance().getConnexion();
    }


    @Override
    public void add(Clubs club) throws SQLException, Exception{
        String sDateDebut = "31/12/1998";
        java.util.Date dateDebut = new SimpleDateFormat("dd/MM/yyyy").parse(sDateDebut);
        // ajouter une personne dans la bd
        //1 - req SQL done
        //2 - executer la req SQL done
        String qry ="INSERT INTO `club`( `NomClub`, `DateFondation`, `TypeActivite`, `Description`, `NbMembres`, `Active`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,club.getNomClub());
            stm.setTimestamp(2, new Timestamp( club.getDateFondation().getTime()));
            stm.setString(3,club.getTypeActivite());
            stm.setString(4,club.getDescription());
            stm.setInt(5,club.getNbMembres());
            stm.setBoolean(6,club.isActive());

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public ArrayList<Clubs> display() {

        //retourner toute les perosnnes dans la bd
        //1- req SQL done
        //2 -execution de la req done
        // 3- remplire la liste done
        // 4 - retourner la liste done
        ArrayList<Clubs> clubs = new ArrayList();
        String qry ="SELECT * FROM `club`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Clubs c = new Clubs();
                c.setIdClub(rs.getInt(1));
                c.setNomClub(rs.getString(2));
                c.setDateFondation(rs.getDate(3));
                c.setTypeActivite(rs.getString(4));
                c.setDescription(rs.getString(5));
                c.setNbMembres(rs.getInt(6));
                c.setActive(rs.getBoolean(7));
                clubs.add(c);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clubs;
    }

    @Override
    public void update(Clubs club) {
        String qry = "UPDATE club SET NomClub=?, DateFondation=?, TypeActivite=?, Description=?, NbMembres=?, Active=? WHERE IdClub=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,club.getNomClub());
            stm.setTimestamp(2, new Timestamp( club.getDateFondation().getTime()));
            stm.setString(3,club.getTypeActivite());
            stm.setString(4,club.getDescription());
            stm.setInt(5,club.getNbMembres());
            stm.setBoolean(6,club.isActive());
            stm.setInt(7,club.getIdClub());
            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    @Override
    public void delete(Clubs club) {
        try {
            String qry="DELETE FROM club WHERE IdClub=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,club.getIdClub());
            stm.executeUpdate();
            System.out.println("club est supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
