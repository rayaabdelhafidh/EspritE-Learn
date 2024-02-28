package tn.esprit.utils;

import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Enum.Heure;
import tn.esprit.Models.Enum.JourSemaine;
import tn.esprit.Models.Matiere;
import tn.esprit.Models.Salle;
import tn.esprit.Models.emploi;
import tn.esprit.Services.ServiceEmploi;
import tn.esprit.Services.ServiceSalle;
import tn.esprit.utils.MyDataBase;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        MyDataBase b = MyDataBase.getInstance();

//        Salle s1 = new Salle(Bloc.A,105);
//        Salle s2 = new Salle(Bloc.D,306);
//        Salle s3 = new Salle(Bloc.F,200);
//        Salle s4 = new Salle(Bloc.D,100);
        Salle s5 = new Salle(Bloc.E,300);
        Salle s6 = new Salle(Bloc.E,300);
       // Salle s7 = new Salle(100L,Bloc.M,200);
//
       ServiceSalle SS = new ServiceSalle();
//        SS.ajouterSalle(s1);
//        SS.ajouterSalle(s2);
//        SS.ajouterSalle(s3);
 //      SS.ajouterSalle(s4);
   //    SS.ajouterSalle(s5);
     // SS.ajouterSalle(s6);
   //   SS.ajouterSalle(s7);

     //   System.out.println(SS.afficherTousSalles());

//       try {
//           s5.setSalleId(19);
//          s5.setNumeroSalle(401);
//           SS.modifierSalle(s5);
//        } catch (NullPointerException e){
//           System.out.println(e.getMessage());
//        }

//        try{
//            s6.setSalleId(20);
//            SS.supprimerSalle(s6);
//        } catch (NullPointerException e){
//            System.out.println(e.getMessage());
//        }

//        SS.modifierSalle(s3);
//        Salle s4 = new Salle();
//        s4.setBloc(Bloc.G);
//        s4.setNumeroSalle(101);
//        SS.modifierSalle(s4);

        // Test supprimerSalle
//        boolean isDeleted = serviceSalle.supprimerSalle(salle);
//        if (isDeleted) {
//            System.out.println("Salle deleted successfully.");
//        } else {
//            System.out.println("Failed to delete salle.");
//        }
//    }

//
//        Matiere matiere = new Matiere(1, "Programmation Java");
//        Date premierDate = new Date(124, 1, 15, 10, 0, 0);
//        Date dernierDate = new Date(124, 1, 15, 12, 0, 0);
//        Salle salle = new Salle(1,Bloc.A,105);
//        Set<JourSemaine> jours = new HashSet<>();
//        jours.add(JourSemaine.LUNDI);
//        jours.add(JourSemaine.MARDI);
//        jours.add(JourSemaine.MERCREDI);
//        jours.add(JourSemaine.JEUDI);
//        jours.add(JourSemaine.VENDREDI);
//        Set<Heure> heures = new HashSet<>();
//        heures.add(Heure.PREMIERE_SEANCE);
//        heures.add(Heure.DEUXIEME_SEANCE);
//        heures.add(Heure.TROISIEME_SEANCE);
//        heures.add(Heure.QUATRIEME_SEANCE);
//        emploi e1 = new emploi(1, matiere, salle, premierDate, dernierDate, jours, heures);
//        ServiceEmploi serviceEmploi = new ServiceEmploi();
//        serviceEmploi.ajouterEmploi(e1);


//
//        emploi emploi = new emploi();
//        emploi.setPremierDate(new Date(124, 2, 12, 10, 0, 0));
//        emploi.setDernierDate(new Date(124, 2, 18, 10, 0, 0));
//
//        Matiere matiere = new Matiere();
//        matiere.setIdM(2);
//        emploi.setMatiere(matiere);
//
//        Salle salle = new Salle();
//        salle.setSalleId(3);
//        emploi.setSalle(salle);
//
//        Set<JourSemaine> jourSemaineSet = new HashSet<>();
//        jourSemaineSet.add(JourSemaine.LUNDI);
//        jourSemaineSet.add(JourSemaine.MARDI);
//        jourSemaineSet.add(JourSemaine.MERCREDI);
//        jourSemaineSet.add(JourSemaine.JEUDI);
//        jourSemaineSet.add(JourSemaine.VENDREDI);
//        emploi.setJourSemaine(jourSemaineSet);
//
//        Set<Heure> heureSet = new HashSet<>();
//        heureSet.add(Heure.PREMIERE_SEANCE);
//        heureSet.add(Heure.DEUXIEME_SEANCE);
//        heureSet.add(Heure.TROISIEME_SEANCE);
//        heureSet.add(Heure.QUATRIEME_SEANCE);
//        emploi.setHeure(heureSet);
//
//        ServiceEmploi serviceEmploi = new ServiceEmploi();
//        serviceEmploi.ajouterEmploi(emploi);
//
        ServiceEmploi SE = new ServiceEmploi();
        System.out.println(SE.getAll());
        System.out.println(SE.getById(8));
//
//        try {
//            emploi e1 = new emploi();
//            e1.setEmploiId(4);
//        e1.setPremierDate(new Date());
//        e1.setDernierDate(new Date());
//        Matiere matiere = new Matiere();
//        matiere.setIdM(1);
//        e1.setMatiere(matiere);
//            Salle s1 = new Salle();
//            s1.setSalleId(21);
//            e1.setSalle(s1);
//        Set<JourSemaine> jourSemaineSet = new HashSet<>();
//        e1.setJourSemaine(jourSemaineSet);
//        Set<Heure> heureSet = new HashSet<>();
//        e1.setHeure(heureSet);
//
//            SE.modifierEmploi(e1);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//
//        System.out.println("aprés modif");
//        System.out.println(SE.getById(4));


//
//        Matiere matiere = new Matiere(1, "SGBD");
//        Date premierDate = new Date(124, 2, 16, 10, 0, 0);
//        Date dernierDate = new Date(124, 2, 20, 12, 0, 0);
//        Salle salle = new Salle(2,Bloc.G,205);
//        Set<JourSemaine> jours = new HashSet<>();
//        jours.add(JourSemaine.LUNDI);
//        jours.add(JourSemaine.MARDI);
//        jours.add(JourSemaine.MERCREDI);
//        jours.add(JourSemaine.JEUDI);
//        jours.add(JourSemaine.VENDREDI);
//        Set<Heure> heures = new HashSet<>();
//        heures.add(Heure.PREMIERE_SEANCE);
//        heures.add(Heure.DEUXIEME_SEANCE);
//        heures.add(Heure.TROISIEME_SEANCE);
//        heures.add(Heure.QUATRIEME_SEANCE);
//        emploi e1 = new emploi(1, matiere, salle, premierDate, dernierDate, jours, heures);
//        ServiceEmploi serviceEmploi = new ServiceEmploi();
//        serviceEmploi.ajouterEmploi(e1);

//       emploi emploiToDelete = SE.getById(5);
//       if (emploiToDelete!=null){
//           boolean isDeleted = SE.supprimerEmploi(emploiToDelete);
//        if (isDeleted) {
//            System.out.println("Emploi deleted successfully.");
//        } else {
//            System.out.println("Failed to delete emploi.");
//        }
//    } else {
//        System.out.println("Emploi not found.");
//    }
    }
}