package tn.esprit.Services;

import tn.esprit.Interfaces.IServiceSalle;
import tn.esprit.Models.Enum.Bloc;
import tn.esprit.Models.Salle;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class ServiceSalle implements IServiceSalle<Salle> {

    private Connection cnx ;
    public ServiceSalle(){
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouterSalle(Salle salle) {
        String qry = "INSERT INTO `salle`(`bloc`, `numeroSalle`) VALUES (?,?)";

        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,salle.getBloc().name());
            stm.setInt(2,salle.getNumeroSalle());
            stm.executeUpdate();
        } catch (SQLException e) {
       System.out.println(e.getMessage());
    }}


    @Override
    public ArrayList<Salle> afficherTousSalles() {
        ArrayList<Salle> salles = new ArrayList<>();
        String qry = "SELECT bloc, numeroSalle FROM `salle`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Salle s = new Salle();
//                s.setSalleId(rs.getInt("salleId"));
                // traja3 el valeur mta3 bloc ka string m BD
                //5ater el BD k traja3 valeur traja3ha de types primitives, string int etc mouch enum
                String blocString = rs.getString("bloc");
                // Convert the string to the Bloc enum using valueOf()
                Bloc bloc = Bloc.valueOf(blocString);
                // Set the Bloc enum value
                s.setBloc(bloc);
                s.setNumeroSalle(rs.getInt("numeroSalle"));

                salles.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salles;
    }
    @Override
    public void modifierSalle(Salle salle) {
        try {
            String qry = "UPDATE `salle` SET `bloc`=?, `numeroSalle`=? WHERE `salleId`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, salle.getBloc().name());
            stm.setInt(2, salle.getNumeroSalle());
            stm.setInt(3, salle.getSalleId());
            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
               System.out.println("Salle " + salle.getSalleId() + " Modifiée !");
            } else {
                System.out.println("Aucune salle modifiée !");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("salem");
        }}

    @Override
    public boolean supprimerSalle(Salle salle) {
        String qry = "DELETE FROM `salle` WHERE `salleId`=?";
        try (PreparedStatement stm = cnx.prepareStatement(qry)) {
            stm.setInt(1, salle.getSalleId());
            int sallesSupprimés = stm.executeUpdate();
            // najem nraja3 true mais mouch 5ater ma raja3lich exception ya3ni sar el suppression
            //k traja3 num akther men 0 net2aked enou hya saret bel7a9
            return sallesSupprimés > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
//najem na3mel nafs el faza l modifierSalle ama 3amla el méthode void
    @Override
    public Salle getById(int Id) {
        return null;
    }
}
