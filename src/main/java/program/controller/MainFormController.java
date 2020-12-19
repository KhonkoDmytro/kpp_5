package program.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import program.service.Service;

import java.io.File;

public class MainFormController {
    Service service = Service.getInstance();
    @FXML
    private Spinner<Integer> shapeSpeed, engineSpeed, accessorySpeed;

    @FXML
    public void initialize() {
        int initialValue = 1;
        shapeSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engineSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        accessorySpeed.setValueFactory(getSpinnerValueFactory(initialValue));
    }

    private SpinnerValueFactory<Integer> getSpinnerValueFactory(int initialValue) {
        SpinnerValueFactory<Integer> spinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
        spinnerValueFactory.valueProperty().addListener(observable -> setEngineFactorySpeed());
        return spinnerValueFactory;
    }

    @FXML
    public void openConfig() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("only xml","*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Відкрити файл конфігурації");
        File config = fileChooser.showOpenDialog(new Stage());
        service.openFile(config);
    }

    @FXML
    public void setEngineFactorySpeed() {
        int value = engineSpeed.getValue();
    }
}
