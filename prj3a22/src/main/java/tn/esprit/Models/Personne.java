package tn.esprit.Models;

public class Personne {
    private int idP,age;
    private String nom,prenom;

    public Personne() {

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

    public Personne(int idP, int age, String nom, String prenom) {
        this.idP = idP;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;

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

}
