module tn.esprit.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires javafx.web;
    requires json.simple;
    requires org.json;
    requires twilio;

    exports tn.esprit.esprit.test;
    opens tn.esprit.esprit.test to javafx.fxml;
    exports tn.esprit.esprit.controller;
    opens tn.esprit.esprit.controller to javafx.fxml;
    exports tn.esprit.esprit;
    opens tn.esprit.esprit to javafx.fxml;
}