package tn.esprit.Models;

public class Matiere {

    private int MatiereId;
    private String nomMatiere;

    public Matiere(){}
    public Matiere(int matiereId, String nomMatiere) {
        MatiereId = matiereId;
        this.nomMatiere = nomMatiere;
    }

    public Matiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public int getMatiereId() {
        return MatiereId;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setMatiereId(int matiereId) {
        MatiereId = matiereId;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "MatiereId=" + MatiereId +
                ", nomMatiere='" + nomMatiere + '\'' +
                '}';
    }
}
