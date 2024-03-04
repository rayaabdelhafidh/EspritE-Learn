module tn.esprit.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires org.controlsfx.controls;


    exports tn.esprit.esprit.test;
    opens tn.esprit.esprit.test to javafx.fxml;
    exports tn.esprit.esprit.controller;
    opens tn.esprit.esprit.controller to javafx.fxml;
    exports tn.esprit.esprit;
    opens tn.esprit.esprit to javafx.fxml;





}
