module tn.esprit.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports tn.esprit.esprit.test;
    opens tn.esprit.esprit.test to javafx.fxml;
}