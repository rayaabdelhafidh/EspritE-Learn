package tn.esprit.esprit.models;

import java.util.Objects;

public class PlanDetude {
    private int id;
    private String nomProgramme;
    private Niveau niveau;
    private int dureeTotal;
    private int creditsRequisTotal;

    public PlanDetude() {
    }

    public PlanDetude(String nomProgramme, Niveau niveau, int dureeTotal, int creditsRequisTotal) {
        this.nomProgramme = nomProgramme;
        this.niveau = niveau;
        this.dureeTotal = dureeTotal;
        this.creditsRequisTotal = creditsRequisTotal;
    }

    public PlanDetude(int id, String nomProgramme, Niveau niveau, int dureeTotal, int creditsRequisTotal) {
        this.id = id;
        this.nomProgramme = nomProgramme;
        this.niveau = niveau;
        this.dureeTotal = dureeTotal;
        this.creditsRequisTotal = creditsRequisTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProgramme() {
        return nomProgramme;
    }

    public void setNomProgramme(String nomProgramme) {
        this.nomProgramme = nomProgramme;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public int getDureeTotal() {
        return dureeTotal;
    }

    public void setDureeTotal(int dureeTotal) {
        this.dureeTotal = dureeTotal;
    }

    public int getCreditsRequisTotal() {
        return creditsRequisTotal;
    }

    public void setCreditsRequisTotal(int creditsRequisTotal) {
        this.creditsRequisTotal = creditsRequisTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanDetude that)) return false;
        return getId() == that.getId() && Objects.equals(getNomProgramme(), that.getNomProgramme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomProgramme());
    }

    @Override
    public String toString() {
        return "Plandetude{" +
                "id=" + id +
                ", nomProgramme='" + nomProgramme + '\'' +
                ", niveau=" + niveau +
                ", dureeTotal=" + dureeTotal +
                ", creditsRequisTotal=" + creditsRequisTotal +
                '}';
    }
}
