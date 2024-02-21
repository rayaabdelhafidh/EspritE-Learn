package tn.esprit.Models;

import tn.esprit.Models.Enum.Bloc;

public class Salle {
    private int salleId;
    private Bloc bloc;
    private int numeroSalle; //hedha fih el etage w num classe ya3ni ma test7a9ech attribut etage. toul 106

    public Salle(int salleId, Bloc bloc, int numeroSalle) {
        this.salleId = salleId;
        this.bloc = bloc;
        this.numeroSalle = numeroSalle;
    }

    public Salle(Bloc bloc, int numeroSalle) {
        this.bloc = bloc;
        this.numeroSalle = numeroSalle;
    }

    public Salle() {

    }

    public int getSalleId() {
        return salleId;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }


    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "salleId=" + salleId +
                ", bloc=" + bloc +
                ", numeroSalle=" + numeroSalle +
                '}';
    }
}
