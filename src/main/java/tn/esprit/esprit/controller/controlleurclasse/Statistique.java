package tn.esprit.esprit.controller.controlleurclasse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.esprit.models.modelsclasse.filiere;
import tn.esprit.esprit.services.serviceclasse.ServiceClasse;


import java.util.HashMap;
import java.util.Map;

public class Statistique {
    @FXML
    private PieChart pi_chart;

    private final ServiceClasse classeService = new ServiceClasse();

    @FXML
    void initialize() {
        // Récupérer la répartition des classes par filière
        Map<filiere, Integer> classDistributionByFiliere = getClassDistributionByFiliere();

        // Créer une liste de segments de PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Ajouter les données de répartition à la liste
        for (Map.Entry<filiere, Integer> entry : classDistributionByFiliere.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }

        // Ajouter les données au PieChart
        pi_chart.setData(pieChartData);
    }

    private Map<filiere, Integer> getClassDistributionByFiliere() {
        Map<filiere, Integer> classDistributionByFiliere = new HashMap<>();
        // Récupérer la répartition des classes par filière depuis le service
        Map<filiere, Integer> distributionMap = classeService.getClassDistributionByFiliere();
        classDistributionByFiliere.putAll(distributionMap);
        return classDistributionByFiliere;
    }


}
