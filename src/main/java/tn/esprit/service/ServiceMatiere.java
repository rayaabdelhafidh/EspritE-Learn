package tn.esprit.service;

import tn.esprit.iservice.IService;
import tn.esprit.models.Matiere;
import tn.esprit.models.Niveau;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceMatiere implements IService<Matiere> {

    Connection cnx;

    public ServiceMatiere() {
      cnx= MyDataBase.getInstance().getCnx();
    }

    @Override
    public void ajouter(Matiere matiere) {
     try{
         String query="INSERT INTO `matiere`" +
                 "(`nomM`, `idEnseignant`," +
                 " `nbrHeure`, `coefficient`, " +
                 "`semester`, `credit`, " +
                 "`idPlanDetude`) " +
                 "VALUES (?,?,?,?,?,?,?)";

                /* "INSERT INTO `matiere`" +
                 "(`nomM`, `idEnseignant`," +
                 "`nbrHeure`, `coefficient`," +
                 "`niveau`, `semester`) " +
                 "VALUES (?,?,?,?,?,?)";*/
         PreparedStatement pst=cnx.prepareStatement(query);
          pst.setString(1,matiere.getNomM());
          pst.setInt(2,matiere.getIdEnseignant());
         pst.setInt(3,matiere.getNbrHeure());
         pst.setInt(4,matiere.getCoefficient());
         pst.setInt(5,matiere.getSemester());
         pst.setInt(6,matiere.getCredit());
         pst.setInt(7,matiere.getIdPlanDetude());
         pst.executeUpdate();
     }
     catch(SQLException e){
         System.out.println("erreur"+e.getMessage());
     }
    }

    @Override
    public void modifier(Matiere matiere) {
        try{
            String query="UPDATE `matiere` SET" +
                    "`nomM`=?," +
                    "`idEnseignant`=?," +
                    "`nbrHeure`=?," +
                    "`coefficient`=?," +
                    "`semester`=?," +
                    "`credit`=?," +
                    "`idPlanDetude`=? " +
                    "WHERE idM=?";
                    /*"UPDATE `matiere` SET " +
                    "`nomM`=?," +
                    "`idEnseignant`=?," +
                    "`nbrHeure`=?," +
                    "`coefficient`=?," +
                    "`semester`=? " +
                    "`credit`=? " +
                    "`idPlanDetude`=? " +
                    "WHERE idM=?";*/


            PreparedStatement pst= cnx.prepareStatement(query);
            pst.setString(1,matiere.getNomM());
            pst.setInt(2,matiere.getIdEnseignant());
            pst.setInt(3,matiere.getNbrHeure());
            pst.setInt(4,matiere.getCoefficient());
            pst.setInt(5,matiere.getSemester());
            pst.setInt(6,matiere.getCredit());
            pst.setInt(7,matiere.getIdPlanDetude());
            pst.setInt(8,matiere.getIdM());
            pst.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("erreur"+e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try{
            String query="DELETE FROM `matiere` WHERE idM=?";
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setInt(1,id);
            pst.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("erreur"+e.getMessage());
        }
    }

    @Override
    public Set<Matiere> afficher() {
        Set<Matiere> matiereSet=new HashSet<>();
        try{
            String query="SELECT * FROM `matiere`";
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(query);
            while (rs.next()){
                Matiere m=new Matiere();
                m.setIdM(rs.getInt("idM"));
                m.setNomM(rs.getString("nomM"));
                m.setIdEnseignant(rs.getInt("idEnseignant"));
                m.setNbrHeure(rs.getInt("nbrHeure"));
                m.setCoefficient(rs.getInt("coefficient"));
                //m.setNiv(rs.getString("niveau"));
                //m.setNiv(Niveau.valueOf(rs.getString("niveau")));
                m.setSemester(rs.getInt("semester"));
               m.setCredit(rs.getInt("credit"));
               m.setIdEnseignant(rs.getInt("idPlanDetude"));
                matiereSet.add(m);
            }
        }
        catch(SQLException e){
            System.out.println("erreur"+e.getMessage());
        }
        return matiereSet;
    }

    @Override
    public Matiere getById(int id) {

        return afficher().stream().filter(p->p.getIdM()==id).findAny().orElse(null);

    }
    public List<String> getNomMatiere(){
        return afficher().stream().map(m->m.getNomM()).collect(Collectors.toList());
    }

    public int getIdByName(String nom){
        return afficher().stream().filter(m->m.getNomM().equals(nom)).findAny().orElse(null).getIdM();
    }
}
