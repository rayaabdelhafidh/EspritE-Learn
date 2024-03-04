package tn.esprit.Models;


import java.util.Date;

public class PresenceData {
    private String nomEtudiant;
    private EtatPresence etatPresence;
    private Date datePresence;

    public PresenceData(String nomEtudiant, EtatPresence etatPresence, Date datePresence) {
        this.nomEtudiant = nomEtudiant;
        this.etatPresence = etatPresence;
        this.datePresence = datePresence;
    }

    // Getters et setters

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public EtatPresence getEtatPresence() {
        return etatPresence;
    }

    public void setEtatPresence(EtatPresence etatPresence) {
        this.etatPresence = etatPresence;
    }

    public Date getDatePresence() {
        return datePresence;
    }

    public void setDatePresence(Date datePresence) {
        this.datePresence = datePresence;
    }
}

