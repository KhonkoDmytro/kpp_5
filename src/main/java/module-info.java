module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens program.controller to javafx.fxml;
    opens program to javafx.fxml;
    exports program;
}