package tn.esprit;

import tn.esprit.IServices.ServiceClasse;
import tn.esprit.IServices.ServicePersonne;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.classe;
import tn.esprit.Models.filiere;
import tn.esprit.Models.niveaux;
import tn.esprit.Models.EtatPresence;
import tn.esprit.Models.Seance;


import tn.esprit.utilse.Database;
import tn.esprit.Models.Presence;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args)  {

        Database b = Database.getInstance();


//classe c1=new classe("3A50",filiere.TIC,20,niveaux._3B);
classe c2=new classe(8,"2A5yguygb", filiere.TIC,20,niveaux._2A);
classe c3=new classe("3A55",filiere.TIC,20,niveaux._3B);
//classe c3=new classe("2A6","TIC",20, niveaux._2A);


       // Presence pr=new Presence (EtatPresence.Present,date,Seance.S2,"3A50");
      //  Date currentDate = new Date();

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = formatter.format(currentDate);
       // String formattedDate = formatter.format(currentDate);
        //Presence pr1=new Presence(EtatPresence.Absent, LocalDate.parse(formattedDate),Seance.S2,"2A22");
//        Presence pr2=new Presence(EtatPresence.ABSENT,LocalDate.parse(formattedDate),Seance.S1_S2,"3B");
//        ServicePresence SPr=new ServicePresence();
            // Appeler la méthode addPresence() avec l'objet Presence correctement configuré
         //   SPr.addPresence(pr2);



        ServiceClasse SC=new ServiceClasse();
    ServiceClasse SC1=new ServiceClasse();
   // SC1.add(c2);

//SC.add(c3);
        ServicePersonne SP=new ServicePersonne();

       //System.out.println(SC.getAll());
     try {
          c2.setNbreEtud(100);
          SC.update(c2);
           // SC.delete(15);
            //SC.delete(c2.getidC());
            SC.getClasse(8);
            SC.delete(6);
           // System.out.println(SC.getClasse(10));
            System.out.println(c2.getidC()  +  "id");
            //SC.delete(c3.getId_classe());
          //SC.deleteNom(c3);
          System.out.println(SC.getAll());
          //SC.update(c2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
     }
   }}

