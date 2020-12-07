package program.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import program.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}