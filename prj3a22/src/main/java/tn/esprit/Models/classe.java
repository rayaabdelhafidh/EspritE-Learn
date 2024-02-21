package tn.esprit.Models;

import java.util.List;

public class classe {
    int idC;
    String nomClasse;
    filiere filiere;
    int nbreEtud = 1000;

    niveaux niveaux;
    private List<Presence> presences;


    public classe(String nomClasse,filiere filiere, int nbreEtud,  niveaux niveaux) {
        this.nomClasse = nomClasse;
        this.filiere = filiere;

        this.nbreEtud = nbreEtud;
        this.niveaux = niveaux;
    }

    public classe(int idC, String nomClasse, filiere filiere, int nbreEtud, niveaux niveaux) {
        this.idC = idC;
        this.nomClasse = nomClasse;
        this.filiere = filiere; //(2a,p) enumeration

        this.nbreEtud = nbreEtud;
        this.niveaux = niveaux; //enumeration
    }


    public classe() {

    }


    public int getidC() {
        return idC;
    }

    public void setidC(int idC) {
        this.idC = idC;
    }

    public int getNbreEtud() {
        return nbreEtud;
    }

    public void setNbreEtud(int nbreEtud) {
        this.nbreEtud = nbreEtud;
    }

    public filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(filiere filiere) {
        this.filiere = filiere;
    }

    public niveaux getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(niveaux niveaux) {
        this.niveaux = niveaux;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }


    @Override
    public String toString() {
        return "classe{" +
                "idC =" + idC +
                ", nomClasse='" + nomClasse + '\'' +
                ", filiere='" + filiere + '\'' +
                ", nbreEtud=" + nbreEtud +
                ", niveaux='" + niveaux + '\'' +
                '}';
    }
}


