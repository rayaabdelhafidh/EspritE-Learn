package tn.esprit.esprite_learn.Models;

import java.sql.Date;

public class Participant {
    int idParticipant ;
    int idEvennement;
    int idUser;
    Date dateReservation;
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Participant(int id, int idevennement, int iduser, Date date_reservation) {
        this.idParticipant = id;
        this.idEvennement = idevennement;
        this.idUser = iduser;
        this.dateReservation=date_reservation;
    }

    public Participant(int idevennement, int iduser, Date date_reservation,String token) {
        this.idEvennement = idevennement;
        this.idUser = iduser;
        this.dateReservation = date_reservation;
        this.token=token;
    }

    public int getId() {
        return idParticipant;
    }

    public Integer getIdevennement() {
        return idEvennement;
    }

    public Integer getIduser() {
        return idUser;
    }


    public Date getDate_reservation() {
        return dateReservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.dateReservation = date_reservation;
    }

    public void setId(int id) {
        this.idParticipant = id;
    }

    public void setIdevennement(int idevennement) {
        this.idEvennement = idevennement;
    }

    public void setIduser(int iduser) {
        this.idUser = iduser;
    }

    public Participant() {
    }
}

