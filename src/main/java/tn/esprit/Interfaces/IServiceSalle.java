package tn.esprit.Interfaces;

import tn.esprit.Models.Salle;

import java.util.ArrayList;

public interface IServiceSalle<T> {
    void ajouterSalle (T t);
    ArrayList<T> afficherTousSalles();
    void modifierSalle(T t) ;
    boolean supprimerSalle(T t);
    T getById (int Id);
}
