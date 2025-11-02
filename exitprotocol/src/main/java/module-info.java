module com.theclankers {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;

    opens com.theclankers to javafx.fxml;
    exports com.theclankers;
    exports com.model;
}
