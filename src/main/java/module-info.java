module tn.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens tn.esprit to javafx.fxml;
    exports tn.esprit;
    exports tn.esprit.Controllers;
    opens tn.esprit.Controllers to javafx.fxml;

}