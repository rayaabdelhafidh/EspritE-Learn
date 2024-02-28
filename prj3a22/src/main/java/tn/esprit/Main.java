package tn.esprit;

import tn.esprit.IServices.ServiceClasse;
import tn.esprit.IServices.ServicePersonne;
import tn.esprit.IServices.ServicePresence;
import tn.esprit.Models.Personne;
import tn.esprit.Models.Seance;


import tn.esprit.utilse.Database;
import tn.esprit.Models.Presence;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {

        Database b = Database.getInstance();


//classe c1=new classe("3A50",filiere.TIC,20,niveaux._3B);
//classe c2=new classe(8,"2A5yguygb", filiere.TIC,20,niveaux._2A);
//classe c3=new classe("3A55",filiere.TIC,20,niveaux._3B);

//classe c3=new classe("2A6","TIC",20, niveaux._2A);

Date d1 = new Date(2024,12,1);
       // Presence pr=new Presence (EtatPresence.Present,date,Seance.S2,"3A50");
      //  Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, Calendar.DECEMBER, 21);
        java.util.Date d3 = calendar.getTime();

//  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//     String formattedDate = formatter.format(currentDate);
//       String formattedDate = formatter.format(currentDate);
        Date d2=new Date(2021,12,21);
//        Presence pr2=new Presence(EtatPresence.ABSENT,LocalDate.parse(formattedDate),Seance.S1_S2,"3B");
  ServicePresence SPr=new ServicePresence();
            // Appeler la méthode addPresence() avec l'objet Presence correctement configuré

        Personne Pr1=new Personne(26,30,"marwen","bayach");
ServicePersonne sp1=new ServicePersonne();
sp1.add(Pr1);
        ServiceClasse SC=new ServiceClasse();
    ServiceClasse SC1=new ServiceClasse();
   // SC1.add(c2);

//SC.add(c3);
        ServicePersonne SP=new ServicePersonne();

       //System.out.println(SC.getAll());

   }}

