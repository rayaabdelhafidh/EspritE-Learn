package tn.esprit.Interfaces;

import java.util.Set;

public interface IServiceMatiere<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    Set<T> afficher();
    T getById(int id);
}