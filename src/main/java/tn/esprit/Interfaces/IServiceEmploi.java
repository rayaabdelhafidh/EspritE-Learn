package tn.esprit.Interfaces;

import java.util.ArrayList;

public interface IServiceEmploi<T> {
    void ajouterEmploi (T t);
    ArrayList<T> getAll();

    void modifierEmploi(T t);
    boolean supprimerEmploi(T t);
T getById (int Id);
}
