package program.view;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import program.entity.Engine;

public abstract class View {
    @FXML
    protected Spinner<Integer> shapeSpeed, engineSpeed, accessorySpeed;

    @FXML
    protected TableColumn<Engine,Integer> engine_id;

    @FXML
    protected TableColumn<Engine,String> engine_data,engine_type;

    @FXML
    protected TableView<Engine> engineTable;

    @FXML
    public void initialize() {
        int initialValue = 1;
        shapeSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engineSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        accessorySpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engine_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        engine_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        engine_data.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        engineTable.setStyle("-fx-background-color:lightgreen");
        for (TableColumn<Engine, ?> column : engineTable.getColumns()) {
            column.setStyle("-fx-background-color:lightgreen");
        }
    }
    protected SpinnerValueFactory<Integer> getSpinnerValueFactory(int initialValue) {
        SpinnerValueFactory<Integer> spinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
        spinnerValueFactory.valueProperty().addListener(observable -> setEngineFactorySpeed());
        return spinnerValueFactory;
    }

    @FXML
    public abstract void setEngineFactorySpeed();
}
