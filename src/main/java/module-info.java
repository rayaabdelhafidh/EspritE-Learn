module tn.esprit.esprite_learn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens tn.esprit.esprite_learn to javafx.fxml;
    exports tn.esprit.esprite_learn;
    exports tn.esprit.esprite_learn.Test to javafx.graphics;
    exports tn.esprit.esprite_learn.Controllers;
    opens tn.esprit.esprite_learn.Controllers to javafx.fxml;

}