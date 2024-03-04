package tn.esprit.esprit.services;

import tn.esprit.esprit.iservice.IService;
import tn.esprit.esprit.models.Niveau;
import tn.esprit.esprit.models.PlanDetude;
import tn.esprit.esprit.utils.MyDb;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


    public class ServicePlanDetude implements IService<PlanDetude> {
        private final Connection cnx;

        public ServicePlanDetude() {
            cnx = MyDb.getInstance().getCnx();
        }

        @Override
        public void ajouter(PlanDetude plan) {
            String query = "INSERT INTO plandetude (nomProgramme, niveau, dureeTotal, creditsRequisTotal) VALUES (?, ?, ?, ?)";
            try  {
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setString(1, plan.getNomProgramme());
                pst.setString(2, plan.getNiveau().name());
                pst.setInt(3, plan.getDureeTotal());
                pst.setInt(4, plan.getCreditsRequisTotal());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du plan d'étude: " + e.getMessage());
            }
        }

        @Override
        public void modifier(PlanDetude plan) {
            String query = "UPDATE plandetude SET nomProgramme=?, niveau=?, dureeTotal=?, creditsRequisTotal=? WHERE id=?";
            try {
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setString(1, plan.getNomProgramme());
                pst.setString(2, plan.getNiveau().name());
                pst.setInt(3, plan.getDureeTotal());
                pst.setInt(4, plan.getCreditsRequisTotal());
                pst.setInt(5, plan.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification du plan d'étude: " + e.getMessage());
            }
        }

        @Override
        public void supprimer(int id) {
            String query = "DELETE FROM plandetude WHERE id=?";
            try {
                PreparedStatement pst = cnx.prepareStatement(query);
                pst.setInt(1, id);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression du plan d'étude: " + e.getMessage());
            }
        }

        @Override
        public Set<PlanDetude> afficher() {
            Set<PlanDetude> planSet = new HashSet<>();
            String query = "SELECT * FROM plandetude";

            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    PlanDetude plan = new PlanDetude();
                    plan.setId(rs.getInt("id"));
                    plan.setNomProgramme(rs.getString("nomProgramme"));
                    plan.setNiveau(Niveau.valueOf(rs.getString("niveau")));
                    plan.setDureeTotal(rs.getInt("dureeTotal"));
                    plan.setCreditsRequisTotal(rs.getInt("creditsRequisTotal"));
                    planSet.add(plan);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'affichage des plans d'étude: " + e.getMessage());
            }
            return planSet;
        }

        @Override
        public PlanDetude getById(int id) {
            return afficher().stream().filter(p->p.getId()==id).findAny().orElse(null);
        }

        public List<String> getNomPlanDetude(){
            return afficher().stream().map(p->p.getNomProgramme()).collect(Collectors.toList());
        }
        public int getIdByNomProgramme(String nom){
            return afficher().stream().filter(p->p.getNomProgramme().equals(nom)).findAny().orElse(null).getId();
        }
}
