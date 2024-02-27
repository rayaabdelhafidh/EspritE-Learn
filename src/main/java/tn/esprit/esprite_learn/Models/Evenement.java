package tn.esprit.esprite_learn.Models;
import java.util.Date;

public class Evenement {
    private int IdEvenement;
    private String NomEvenement;
    private Date DateEvenement;
    private String LieuEvenement;
    private int PrixEvenement;
    private String AfficheEvenement;
    private int club;

    public Evenement() {
    }

    public Evenement(int idEvenement, String nomEvenement, Date dateEvenement, String lieuEvenement,int prixEvenement,String AfficheEvenement,int club) {
        idEvenement=this.IdEvenement;
        nomEvenement=this.NomEvenement;
        dateEvenement=this.DateEvenement;
        lieuEvenement=this.LieuEvenement;
        prixEvenement=this.PrixEvenement;
        AfficheEvenement=this.AfficheEvenement;
        club=this.club;
    }

    public Evenement(String nomEvenement, Date dateEvenement, String lieuEvenement,int prixEvenement,String AfficheEvenement,int club) {
        nomEvenement=this.NomEvenement;
        dateEvenement=this.DateEvenement;
        lieuEvenement=this.LieuEvenement;
        prixEvenement=this.PrixEvenement;
        AfficheEvenement=this.AfficheEvenement;
        club=this.club;
    }

    public int getIdEvenement() {
        return IdEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        IdEvenement = idEvenement;
    }

    public String getNomEvenement() {
        return NomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        NomEvenement = nomEvenement;
    }

    public Date getDateEvenement() {
        return DateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        DateEvenement = dateEvenement;
    }

    public String getLieuEvenement() {
        return LieuEvenement;
    }

    public void setLieuEvenement(String lieuEvenement) {
        LieuEvenement = lieuEvenement;
    }

    public int getPrixEvenement() {
        return PrixEvenement;
    }

    public void setPrixEvenement(int prixEvenement) {
        PrixEvenement = prixEvenement;
    }

    public String getAfficheEvenement() {
        return AfficheEvenement;
    }

    public void setAfficheEvenement(String afficheEvenement) {
        AfficheEvenement = afficheEvenement;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "IdEvenement=" + IdEvenement +
                ", NomEvenement='" + NomEvenement + '\'' +
                ", DateEvenement=" + DateEvenement +
                ", LieuEvenement='" + LieuEvenement + '\'' +
                ", PrixEvenement=" + PrixEvenement +
                ", AfficheEvenement='" + AfficheEvenement + '\'' +
                ", club=" + club +
                '}';
    }
}
