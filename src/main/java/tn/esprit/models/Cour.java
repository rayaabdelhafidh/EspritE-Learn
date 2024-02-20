package tn.esprit.models;

import java.util.Objects;

public class Cour {
    private int id;
    private String titre;
    private String description;
    private int duree;
    private String objectif;
    private String image;
    private String coursPdfUrl;
    private int idMatiere;

    public Cour() {
    }

    public Cour(String titre, String description, int duree, String objectif, String image, String coursPdfUrl, int idMatiere) {
        this.titre = titre;
        this.description = description;
        this.duree = duree;
        this.objectif = objectif;
        this.image = image;
        this.coursPdfUrl = coursPdfUrl;
        this.idMatiere = idMatiere;
    }

    public Cour(int id, String titre, String description, int duree, String objectif, String image, String coursPdfUrl, int idMatiere) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.duree = duree;
        this.objectif = objectif;
        this.image = image;
        this.coursPdfUrl = coursPdfUrl;
        this.idMatiere = idMatiere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getCoursPdfUrl() { return coursPdfUrl; }

    public void setCoursPdfUrl(String coursPdfUrl) { this.coursPdfUrl = coursPdfUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cour cour)) return false;
        return getId() == cour.getId() && getDuree() == cour.getDuree() && Objects.equals(getTitre(), cour.getTitre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitre(), getDuree());
    }

    @Override
    public String toString() {
        return "Cour{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", duree=" + duree +
                ", objectif='" + objectif + '\'' +
                ", image='" + image + '\'' +
                ", idMatiere=" + idMatiere +
                '}'+"\n";
    }
}
