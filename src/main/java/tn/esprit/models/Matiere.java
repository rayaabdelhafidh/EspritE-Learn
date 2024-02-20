package tn.esprit.models;

import java.util.Objects;

public class Matiere {
   private int idM;
   private String nomM;
  private   int idEnseignant;
  private   int nbrHeure;
   private int coefficient;
    private int semester;
  private   int credit;
   private int idPlanDetude;

    public Matiere() {

    }

    public Matiere(String nomM, int idEnseignant, int nbrHeure, int coefficient, int semester, int credit, int idPlanDetude) {
        this.nomM = nomM;
        this.idEnseignant = idEnseignant;
        this.nbrHeure = nbrHeure;
        this.coefficient = coefficient;
        this.semester = semester;
        this.credit = credit;
        this.idPlanDetude = idPlanDetude;
    }

    public Matiere(int idM, String nomM, int idEnseignant, int nbrHeure, int coefficient, int semester, int credit, int idPlanDetude) {
        this.idM = idM;
        this.nomM = nomM;
        this.idEnseignant = idEnseignant;
        this.nbrHeure = nbrHeure;
        this.coefficient = coefficient;
        this.semester = semester;
        this.credit = credit;
        this.idPlanDetude = idPlanDetude;
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public String getNomM() {
        return nomM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public int getNbrHeure() {
        return nbrHeure;
    }

    public void setNbrHeure(int nbrHeure) {
        this.nbrHeure = nbrHeure;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }


    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCredit() { return credit; }

    public int getIdPlanDetude() {
        return idPlanDetude;
    }

    public void setCredit(int credit) { this.credit = credit; }

    public void setIdPlanDetude(int idPlanDetude) { this.idPlanDetude = idPlanDetude; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matiere matiere = (Matiere) o;
        return idM == matiere.idM && Objects.equals(nomM, matiere.nomM);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idM, nomM);
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "idM=" + idM +
                ", nomM='" + nomM + '\'' +
                ", idEnseignant=" + idEnseignant +
                ", nbrHeure=" + nbrHeure +
                ", coefficient=" + coefficient +
                ", credit=" + credit +
                ", semester=" + semester +
                ", idPlanDetude=" + idPlanDetude +
                '}';
    }
}
