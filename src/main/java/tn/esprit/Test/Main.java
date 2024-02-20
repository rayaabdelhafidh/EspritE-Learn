package tn.esprit.Test;
import tn.esprit.Models.Clubs;
import tn.esprit.Services.ServiceClub;
import tn.esprit.utils.DataBase;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        DataBase db=DataBase.getInstance();
        Clubs c1=new Clubs(100,"Tuniv",new Date(),"Charite","Desc",50,true);
        Clubs c2=new Clubs(50,"Aiesec",new Date(),"Echange","Desc",120,true);
        Clubs c3=new Clubs(47,"Libertad",new Date(),"Evenementiel","Desc",77,true);

       ServiceClub sc = new ServiceClub();
       sc.add(c1);
        sc.add(c2);
        sc.add(c3);
        ArrayList<Clubs> clubs=sc.display();
        for(Clubs club:clubs)
            System.out.println(club.toString());
        sc.delete(c3);
        for(Clubs club:clubs)
            System.out.println(club.toString());
        sc.update(c2);

    }
}
