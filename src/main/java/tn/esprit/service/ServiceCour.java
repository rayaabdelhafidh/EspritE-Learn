package tn.esprit.service;

import tn.esprit.iservice.IService;
import tn.esprit.models.Cour;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ServiceCour implements IService<Cour> {
    Connection cnx;

    public ServiceCour(){
        cnx= MyDataBase.getInstance().getCnx();

    }
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public void ajouter(Cour cour) {
        /*try {
            String query="INSERT INTO " +
                    "`cour`( `titre`, `description`," +
                    " `duree`, `objectif`, `image`," +
                    " `idMatiere`) " +
                    "VALUES ('"+cour.getTitre()+"'," +
                    "'"+cour.getDescription()+"','"+cour.getDuree()+"','"+cour.getObjectif()+"'," +
                    "'"+cour.getImage()+"','"+cour.getIdMatiere()+"')";

            Statement st = cnx.createStatement();

            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }*/
        try{
            String query="INSERT INTO `cour`" +
                    "(`titre`, `description`," +
                    " `duree`, `objectif`," +
                    " `image`, `idMatiere`," +
                    " `coursPdfUrl`)" +
                    " VALUES (?,?,?,?,?,?,?)";

            PreparedStatement pst= cnx.prepareStatement(query);
            pst.setString(1, cour.getTitre());
            pst.setString(2, cour.getDescription());
            pst.setInt(3, cour.getDuree());
            pst.setString(4, cour.getObjectif());
            pst.setString(5, cour.getImage());
            pst.setString(7, cour.getCoursPdfUrl());
            pst.setInt(6, cour.getIdMatiere());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }


    }

    @Override
    public void modifier(Cour cour) {
        try{
            String query="UPDATE `cour` SET " +
                    "`titre`=?," +
                    "`description`=?," +
                    "`duree`=?," +
                    "`objectif`=?," +
                    "`image`=?," +
                    "`coursPdfUrl`=?," +
                    "`idMatiere`=? WHERE id=?";
            PreparedStatement pst= cnx.prepareStatement(query);
            pst.setString(1, cour.getTitre());
            pst.setString(2, cour.getDescription());
            pst.setInt(3, cour.getDuree());
            pst.setString(4, cour.getObjectif());
            pst.setString(5, cour.getImage());
            pst.setInt(7, cour.getIdMatiere());
            pst.setString(6, cour.getCoursPdfUrl());
            pst.setInt(8,cour.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try{
            String query="DELETE FROM `cour` WHERE id=?";
            PreparedStatement pst= cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("erreur:"+e.getMessage());
        }
    }

    @Override
    public Set<Cour> afficher() {
        Set<Cour> courSet=new HashSet<>();
        try{
            String query="SELECT * FROM `cour`";
            Statement st= cnx.createStatement();
            ResultSet rs=st.executeQuery(query);//type de retoure ta3 executeQuery
            while(rs.next()){
                Cour c=new Cour();
                c.setId(rs.getInt("id"));
                c.setTitre(rs.getString("titre"));
                c.setDescription(rs.getString("description"));
                c.setObjectif(rs.getString("objectif"));
                c.setDuree(rs.getInt("duree"));
                c.setImage(rs.getString("image"));
                c.setCoursPdfUrl(rs.getString("coursPdfUrl"));
                c.setIdMatiere(rs.getInt("idMatiere"));
                courSet.add(c);
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }


        return courSet;

    }

    @Override
    public Cour getById(int id) {
        return afficher().stream().filter(c->c.getId()==id).findAny().orElse(null);
        /*for(Cour c :afficher()){
            if(c.getId()==id){
                return c;
            }
        }
        return null;*/
    }
    public TreeSet<Cour> sortByTitre(){
        return afficher().stream()
                .collect(Collectors.toCollection(()->new TreeSet<>((c1, c2)->c1.getTitre().compareTo(c2.getTitre()))));
    }
}
