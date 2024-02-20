module tn.esprit.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens tn.esprit.esprit to javafx.fxml;
    exports tn.esprit.esprit;
}