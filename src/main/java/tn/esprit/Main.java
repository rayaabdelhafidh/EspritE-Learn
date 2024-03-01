package tn.esprit;

import tn.esprit.IServices.ServicePersonne;
import tn.esprit.utilse.Database;

import java.sql.SQLException;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {


Database database=new Database();

//        ServiceClasse SC=new ServiceClasse();
//    ServiceClasse SC1=new ServiceClasse();
   // SC1.add(c2);

//SC.add(c3);
        ServicePersonne SP=new ServicePersonne();
        System.out.println(SP.getEtudiantsAvecPresence("1A3"));
       //System.out.println(SC.getAll());

   }}

