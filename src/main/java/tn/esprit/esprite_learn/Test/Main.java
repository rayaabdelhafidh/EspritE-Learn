package tn.esprit.esprite_learn.Test;
import tn.esprit.esprite_learn.Models.Clubs;
import tn.esprit.esprite_learn.Models.Evenement;
import tn.esprit.esprite_learn.Services.ServiceClub;
import tn.esprit.esprite_learn.Services.ServiceEvenement;
import tn.esprit.esprite_learn.utils.DataBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        DataBase db=DataBase.getInstance();
        Clubs c1=new Clubs("Tuniv",new Date(),"Charite","Desc",50,true);
        Clubs c2=new Clubs("Aiesec",new Date(),"Echange","Desc",120,true);
        Clubs c3=new Clubs("Libertad",new Date(),"Evenementiel","Desc",77,true);
        Evenement e1=new Evenement("Fantasy", new Date(),"Tozeur",150,"URL",40);

        ServiceClub sc = new ServiceClub();
        /*sc.add(c1);
        sc.add(c2);
        sc.add(c3);*/
        ArrayList<Clubs> clubs=sc.display();
        for(Clubs club:clubs)
            System.out.println(club.toString());
        sc.delete(c3);
        for(Clubs club:clubs)
            System.out.println(club.toString());
        sc.update(c2);
        sc.find("Rotaract");
        ServiceEvenement se=new ServiceEvenement();
        //se.add(e1);
       /* ArrayList<Evenement> evenements=se.display();
        for(Evenement ev:evenements)
            System.out.println(ev.toString());
        se.delete(evenements.get(0));
        for(Evenement ev:evenements)
            System.out.println(ev.toString());*/
    }
}

