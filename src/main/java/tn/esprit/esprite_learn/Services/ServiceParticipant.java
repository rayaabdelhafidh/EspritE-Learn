package tn.esprit.esprite_learn.Services;

import tn.esprit.esprite_learn.IService.IService;
import tn.esprit.esprite_learn.Models.Participant;
import tn.esprit.esprite_learn.utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceParticipant implements IService<Participant> {
    private PreparedStatement pste;

    Connection cnx = DataBase.getInstance().getConnexion();

    public ServiceParticipant() throws SQLException {
    }
    @Override
    public void add(Participant participant) throws Exception {
        String req = "INSERT INTO `participant`(`token`, `dateReservation`, `idUser`, `idEvenement`) VALUES (?,?,?,?)";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(4, participant.getIdevennement());
            pste.setInt(3, participant.getIduser());
            pste.setTimestamp(2, new Timestamp( participant.getDate_reservation().getTime()));
            pste.setString(1, participant.getToken());
            pste.executeUpdate();
            System.out.println("Participation créée");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public ArrayList<Participant> display() {
        ArrayList<Participant> liste = new ArrayList<>();
        String req = "SELECT * FROM participant";
        try {
            pste = cnx.prepareStatement(req);
            ResultSet resultSet = pste.executeQuery();
            while (resultSet.next()) {
                Participant p = new Participant();
                p.setId(resultSet.getInt(1));
                p.setIdevennement(resultSet.getInt(5));
                p.setIduser(resultSet.getInt(4));
                p.setDate_reservation(resultSet.getDate(3));

                liste.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }

    @Override
    public void update(Participant participant) {
        String req = "UPDATE `participant` SET `idParticipant`=?,`token`=?,`dateReservation`=?,`idUser`=?,`idEvenement`=? WHERE idParticipant=?";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(5, participant.getIdevennement());
            pste.setInt(4, participant.getIduser());
            pste.setTimestamp(3, new Timestamp( participant.getDate_reservation().getTime()));
            pste.setInt(1, participant.getId());

            pste.executeUpdate();
            System.out.println("Particpants modifié");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvenement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Participant participant) {
        String req = "DELETE FROM participant WHERE idParticipant=?;";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(1, participant.getId());
            pste.executeUpdate();
            System.out.println("Particpation Supprimé");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean fetchParticipationOfUser(int id_event, int id_user) {
        boolean fetch = false;
        String req = "SELECT * FROM participants where idUser=? and idEvent=?";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(1, id_user); // Définir la valeur pour le premier paramètre
            pste.setInt(2, id_event);
            ResultSet resultSet = pste.executeQuery();
            while (resultSet.next()) {
                fetch=true;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fetch;
    }
}
