module com.theclankers {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    requires junit;

    opens com.theclankers to javafx.fxml;
    opens com.model to junit;
    exports com.theclankers;
    exports com.model;
}
