package tn.esprit.Models;


import tn.esprit.Models.Enum.Heure;
import tn.esprit.Models.Enum.JourSemaine;

import java.util.Date;
import java.util.Set;

public class emploi {

    private int emploiId;
    private Matiere matiere;
    private Salle salle;
    private Date premierDate;
    private Date dernierDate;
    private Set<JourSemaine> jourSemaine;
    private Set<Heure> heure;

    public emploi(){}
    public emploi(int emploiId, Matiere matiere, Salle salle, Date premierDate, Date dernierDate, Set<JourSemaine> jourSemaine, Set<Heure> heure) {
        this.emploiId = emploiId;
        this.matiere = matiere;
        this.salle = salle;
        this.premierDate = premierDate;
        this.dernierDate = dernierDate;
        this.jourSemaine = jourSemaine;
        this.heure = heure;
    }

    public int getEmploiId() {
        return emploiId;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Date getPremierDate() {
        return premierDate;
    }

    public Date getDernierDate() {
        return dernierDate;
    }


    public Salle getSalle() {
        return salle;
    }


//    public String getMatiereNom(){
//        if (matiere!= null){
//            return matiere.getNomMatiere();
//        } else
//            return null;
//    }

    public Set<JourSemaine> getJourSemaine() {
        return jourSemaine;
    }

    public Set<Heure> getHeure() {
        return heure;
    }

    public void setEmploiId(int emploiId) {
        this.emploiId = emploiId;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public void setPremierDate(Date premierDate) {
        this.premierDate = premierDate;
    }

    public void setDernierDate(Date dernierDate) {
        this.dernierDate = dernierDate;
    }

    public void setJourSemaine(Set<JourSemaine> jourSemaine) {
        this.jourSemaine = jourSemaine;
    }

    public void setHeure(Set<Heure> heure) {
        this.heure = heure;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "emploi{" +
                "emploiId=" + emploiId +
                ", matiere=" + matiere.getNomM() +
                ", salle=" + salle +
                ", premierDate=" + premierDate +
                ", dernierDate=" + dernierDate +
                ", jourSemaine=" + jourSemaine +
                ", heure=" + heure +
                '}';
    }
}
