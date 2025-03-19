module group13_cs4050_assignment3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens group13_cs4050_assignment3 to javafx.fxml;
    exports group13_cs4050_assignment3;
}