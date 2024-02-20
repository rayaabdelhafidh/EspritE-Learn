module tn.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens tn.esprit to javafx.fxml;
    exports tn.esprit;
    exports tn.esprit.controllers;
    opens tn.esprit.controllers to javafx.fxml;

}