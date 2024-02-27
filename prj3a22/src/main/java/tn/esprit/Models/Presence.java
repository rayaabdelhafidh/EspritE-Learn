package tn.esprit.Models;

//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Presence {
    private int idPresence; // Primary Key
   // private ArrayList<classe> classes; // Liste des classes
     EtatPresence EtatPresence;
    private Date date;


private classe classe;
    private Seance seance;
    private List<classe> classes; //relation manyToMany
    private String nomClasse;


    public Presence(int idPresence, ArrayList<classe> classes, EtatPresence EtatPresence, Date date, Seance seance) {
        this.idPresence = idPresence;
        this.classes = classes;
        this.EtatPresence = EtatPresence;
        this.date = date;
        this.seance = seance;
    }

    public Presence(EtatPresence EtatPresence, Date date, Seance seance, String nomClasse) {
        if (EtatPresence == null) {
            throw new IllegalArgumentException("L'état de la présence ne peut pas être null !");
        }

        this.EtatPresence = EtatPresence;
        this.date = date;
        this.seance = seance;
        this.nomClasse = nomClasse;
    }

    public Presence() {

    }


    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public Presence(int idPresence, EtatPresence EtatPresence, Date date, Seance seance, List<classe> classes, classe cl) {
        this.idPresence = idPresence;
        this.EtatPresence = EtatPresence;
        this.date = date;
        this.seance = seance;
        this.classes = classes;
    }
    // Getters and Setters

    public classe getClasse() {
        return classe;
    }

    public void setClasse(classe cl) {
        this.classe = cl;
    }

    public int getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(int idPresence) {
        this.idPresence = idPresence;
    }

    public EtatPresence getEtatPresence() {
        return EtatPresence;
    }

    public void setEtatPresence(EtatPresence etatPresence) {
        EtatPresence = etatPresence;
    }

    public EtatPresence getEtat() {
        return EtatPresence;
    }

    public void setEtat(EtatPresence EtatPresence) {
        this.EtatPresence = EtatPresence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }


    public List<classe> getClasses() {
        return classes;
    }

    public void setClasses(List<classe> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Presence{" +
                "idPresence=" + idPresence +
                ", etat=" + EtatPresence +
                ", date=" + date +
                ", classe=" + classe +
                ", seance=" + seance +
                ", classes=" + classes +
                ", nomClasse='" + nomClasse + '\'' +
                '}';
    }
}
