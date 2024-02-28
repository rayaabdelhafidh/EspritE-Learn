package tn.esprit.Models;

public class Personne {
    private int idP,age,idClasse;

    private String nom,prenom;
    private classe classeEtudiants;
    EtatPresence EtatPresence;


    public Personne() {

    }

    public Personne(int idPersonne, String nom, String prenom) {
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

    public int getIdClasse() {
        return this.idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "idP=" + idP +
                ", age=" + age +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public void getIdClasse(int idClasse) {
    }


}
