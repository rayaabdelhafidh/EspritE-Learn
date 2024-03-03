package tn.esprit.Models;

import java.util.Date;
import java.util.List;

public class Presence {
    private int idPresence; // Primary Key
    private Date date;
    private classe classe;
    private Seance seance;
    private List<classe> classes; //relation manyToMany
    private String nomClasse;
    private int idClasse;

    public Presence(int idPresence, List<classe> classes, Date date, Seance seance) {
        this.idPresence = idPresence;
        this.classes = classes;
        this.date = date;
        this.seance = seance;
    }


    public Presence(int idPresence, Date date, tn.esprit.Models.classe classe, Seance seance, List<tn.esprit.Models.classe> classes, String nomClasse, int idClasse) {
        this.idPresence = idPresence;
        this.date = date;
        this.classe = classe;
        this.seance = seance;
        this.classes = classes;
        this.nomClasse = nomClasse;
        this.idClasse = idClasse;
    }


    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public Presence(Date date, Seance seance, String nomClasse) {
        this.date = date;
        this.seance = seance;
        this.nomClasse = nomClasse;
    }



    public Presence() {}

    public int getIdPresence() {
        return idPresence;
    }

    public void setIdPresence(int idPresence) {
        this.idPresence = idPresence;
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

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public classe getClasse() {
        return classe;
    }

    public void setClasse(classe classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Presence{" +

                " date=" + date +

                ", seance=" + seance +

                ", nomClasse='" + nomClasse + '\'' +

                '}';
    }





}
