package tn.esprit.esprit.iservice;

import java.util.Set;

public interface IService<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    Set<T> afficher();
    T getById(int id);
}

