module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens program.controller to javafx.fxml;
    opens program.view to javafx.fxml;
    opens program to javafx.fxml;
    opens program.entity to javafx.base;
    opens program.logic to javafx.base;
    opens program.factory to javafx.base;
    exports program;
}