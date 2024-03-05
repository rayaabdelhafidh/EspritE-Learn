package tn.esprit.esprite_learn.Services;

import tn.esprit.esprite_learn.IService.IService;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.utils.DataBase;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServiceClub implements IService<Clubs> {

    private Connection cnx ;
    public ServiceClub() throws SQLException {
        cnx =DataBase.getInstance().getConnexion();
    }


    @Override
    public void add(Clubs club) throws Exception{
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
            stm.setTimestamp(2, new Timestamp(club.getDateFondation().getTime()));
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

    public Clubs find(String nom) {
        ArrayList<Clubs> clubs = new ArrayList();
        String qry = "SELECT `IdClub`, `NomClub`, `DateFondation`, `TypeActivite`, `Description`, `NbMembres`, `Active` FROM `club` WHERE NomClub=?";
        Clubs c;
        try {
            PreparedStatement ps = cnx.prepareStatement(qry);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            c = new Clubs();
            while (rs.next()) {
                c.setIdClub(rs.getInt(1));
                c.setNomClub(rs.getString(2));
                c.setDateFondation(rs.getDate(3));
                c.setTypeActivite(rs.getString(4));
                c.setDescription(rs.getString(5));
                c.setNbMembres(rs.getInt(6));
                c.setActive(rs.getBoolean(7));
                System.out.println("Le club cherchée est : " + c.toString());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }
    public Clubs findbyId(int id) {
        ArrayList<Clubs> clubs = new ArrayList();
        String qry = "SELECT `IdClub`, `NomClub`, `DateFondation`, `TypeActivite`, `Description`, `NbMembres`, `Active` FROM `club` WHERE IdClub=?";
     Clubs c;
        try {
            PreparedStatement ps = cnx.prepareStatement(qry);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            c = new Clubs();
            while (rs.next()) {
                c.setIdClub(rs.getInt(1));
                c.setNomClub(rs.getString(2));
                c.setDateFondation(rs.getDate(3));
                c.setTypeActivite(rs.getString(4));
                c.setDescription(rs.getString(5));
                c.setNbMembres(rs.getInt(6));
                c.setActive(rs.getBoolean(7));
                System.out.println("Le club cherchée est : " + c.toString());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }

    public int getIdByName(String nom){
        return display().stream().filter(m->m.getNomClub().equals(nom)).findAny().orElse(null).getIdClub();
    }

    public ArrayList<String> getNom(){
        return (ArrayList<String>) display().stream().map(m->m.getNomClub()).collect(Collectors.toList());
    }

    public Clubs ChercherClub(String nom){
        ArrayList<tn.esprit.esprite_learn.Models.Evenement> evenements = new ArrayList();
        String qry = "SELECT `IdClub`, `NomClub`, `DateFondation`, `TypeActivite`, `Description`, `NbMembres`, `Active` FROM `club` WHERE NomClub=?";
        Clubs c;
        try {
            PreparedStatement ps = cnx.prepareStatement(qry);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            c = new Clubs();
            while (rs.next()) {
                c.setIdClub(rs.getInt(1));
                c.setNomClub(rs.getString(2));
                c.setDateFondation(rs.getDate(3));
                c.setTypeActivite(rs.getString(4));
                c.setDescription(rs.getString(5));
                c.setNbMembres(rs.getInt(6));
                c.setActive(rs.getBoolean(7));
                System.out.println("Le club cherché est : " + c.toString());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }
}
