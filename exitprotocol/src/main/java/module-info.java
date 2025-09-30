module com.theclankers {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.theclankers to javafx.fxml;
    exports com.theclankers;
}
