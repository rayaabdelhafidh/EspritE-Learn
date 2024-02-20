package tn.esprit.Models;

import java.util.Date;

public class Clubs {
    private int IdClub;
    private String NomClub;

    private Date DateFondation;
    private String TypeActivite;
    private String Description;
    private int NbMembres;
    private boolean Active;

    public Clubs() {
    }

    public Clubs(int idClub, String nomClub, Date dateFondation, String typeActivite, String description, int nbMembres, boolean active) {
        IdClub = idClub;
        NomClub = nomClub;
        DateFondation = dateFondation;
        TypeActivite = typeActivite;
        Description = description;
        NbMembres = nbMembres;
        Active = active;
    }
    public int getIdClub() {
        return IdClub;
    }

    public void setIdClub(int idClub) {
        IdClub = idClub;
    }

    public String getNomClub() {
        return NomClub;
    }

    public void setNomClub(String nomClub) {
        NomClub = nomClub;
    }

    public Date getDateFondation() {
        return DateFondation;
    }

    public void setDateFondation(Date dateFondation) {
        DateFondation = dateFondation;
    }

    public String getTypeActivite() {
        return TypeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        TypeActivite = typeActivite;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getNbMembres() {
        return NbMembres;
    }

    public void setNbMembres(int nbMembres) {
        NbMembres = nbMembres;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    @Override
    public String toString() {
        return "Clubs{" +
                "IdClub=" + IdClub +
                ", NomClub='" + NomClub + '\'' +
                ", DateFondation='" + DateFondation + '\'' +
                ", TypeActivite='" + TypeActivite + '\'' +
                ", Description='" + Description + '\'' +
                ", NbMembres=" + NbMembres +
                ", Active=" + Active +
                '}';
    }
}
