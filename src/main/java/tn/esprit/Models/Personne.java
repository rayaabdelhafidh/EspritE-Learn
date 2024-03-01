package tn.esprit.Models;

public class Personne {
    private int idP,age,idClasse;

    private String nom,prenom;
    private classe classeEtudiants;
    EtatPresence EtatPresence;


    public Personne() {

    }

    public Personne(int idP, String nom, String prenom) {
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public classe getClasseEtudiants() {
        return classeEtudiants;
    }

    public void setClasseEtudiants(classe classeEtudiants) {
        this.classeEtudiants = classeEtudiants;
    }

    public tn.esprit.Models.EtatPresence getEtatPresence() {
        return EtatPresence;
    }

    public void setEtatPresence(tn.esprit.Models.EtatPresence etatPresence) {
        EtatPresence = etatPresence;
    }

    public Personne(int idP, int age, int idClasse, String nom, String prenom) {
        this.idP = idP;
        this.age = age;
        this.idClasse = idClasse;
        this.nom = nom;
        this.prenom = prenom;
    }



    public Personne(int idClasse, int age, String nom, String prenom) {
        this.idClasse = idClasse;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;

    }

    public Personne(int age, String nom, String prenom, tn.esprit.Models.EtatPresence etatPresence) {
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        EtatPresence = etatPresence;
    }

    public int getIdClasse() {
        return this.idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "age=" + age +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", EtatPresence=" + EtatPresence +
                '}';
    }

    public void getIdClasse(int idClasse) {
    }



}
