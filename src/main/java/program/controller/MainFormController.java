package program.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import program.logic.Engine;
import program.service.Service;

import java.io.File;
import java.util.List;

public class MainFormController {
    Service service = Service.getInstance();
    @FXML
    private Spinner<Integer> shapeSpeed, engineSpeed, accessorySpeed;

    @FXML
    private TableColumn<Engine,Integer> engine_id;

    @FXML
    private TableColumn<Engine,String> engine_data,engine_type;

    @FXML
    private TableView<Engine> engineTable;

    @FXML
    public void initialize() {
        int initialValue = 1;
        shapeSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engineSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        accessorySpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engine_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        engine_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        engine_data.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
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
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("only xml", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Відкрити файл конфігурації");
        File config = fileChooser.showOpenDialog(new Stage());
        service.openFile(config);
    }

    @FXML
    public void setEngineFactorySpeed() {
        int value = engineSpeed.getValue();
        service.setEngineFactorySpeed(5000/value);
    }

    @FXML
    public void setList() {
        List<Engine> test = service.getEngines();
        ObservableList<Engine> list = FXCollections.observableArrayList(service.getEngines());
        engineTable.setItems(list);
    }


}
