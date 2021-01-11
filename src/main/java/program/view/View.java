package program.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import program.entity.Accessory;
import program.entity.Car;
import program.entity.CarBody;
import program.entity.Engine;

public abstract class View {
    @FXML
    protected Spinner<Integer> shapeSpeed, engineSpeed, accessorySpeed, dealerSpeed;

    @FXML
    protected TableColumn<Engine, Integer> engineId;

    @FXML
    protected TableColumn<Engine, String> engineData, engineType;

    @FXML
    protected TableColumn<Accessory, Integer> accessoryId;

    @FXML
    protected TableColumn<Accessory, String> accessoryData, accessoryType;

    @FXML
    protected TableColumn<CarBody, Integer> bodyId;

    @FXML
    protected TableColumn<CarBody, String> bodyData, bodyType;

    @FXML
    protected TableColumn<Car, Integer> carId;

    @FXML
    protected TableColumn<Car, String> carData, carType;

    @FXML
    protected TableView<Engine> engineTable;

    @FXML
    protected TableView<CarBody> bodyTable;

    @FXML
    protected TableView<Accessory> accessoryTable;

    @FXML
    protected TableView<Car> carTable;

    protected ObservableList<Engine> engineList = FXCollections.observableArrayList();
    protected ObservableList<Car> carList = FXCollections.observableArrayList();
    protected ObservableList<CarBody> bodyList = FXCollections.observableArrayList();
    protected ObservableList<Accessory> accessoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        int initialValue = 1;
        engineTable.setItems(engineList);
        carTable.setItems(carList);
        bodyTable.setItems(bodyList);
        accessoryTable.setItems(accessoryList);
        shapeSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        shapeSpeed.getValueFactory().valueProperty().addListener(observable -> setBodyFactorySpeed());
        engineSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        engineSpeed.getValueFactory().valueProperty().addListener(observable -> setEngineFactorySpeed());
        accessorySpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        accessorySpeed.getValueFactory().valueProperty().addListener(observable -> setAccessoryFactorySpeed());
        dealerSpeed.setValueFactory(getSpinnerValueFactory(initialValue));
        dealerSpeed.getValueFactory().valueProperty().addListener(observable -> setDealerSpeed());
        engineId.setCellValueFactory(new PropertyValueFactory<>("id"));
        engineType.setCellValueFactory(new PropertyValueFactory<>("type"));
        engineData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        accessoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accessoryType.setCellValueFactory(new PropertyValueFactory<>("type"));
        accessoryData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        carId.setCellValueFactory(new PropertyValueFactory<>("id"));
        carType.setCellValueFactory(new PropertyValueFactory<>("type"));
        carData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
        bodyId.setCellValueFactory(new PropertyValueFactory<>("id"));
        bodyType.setCellValueFactory(new PropertyValueFactory<>("type"));
        bodyData.setCellValueFactory(new PropertyValueFactory<>("creationTime"));
    }

    protected SpinnerValueFactory<Integer> getSpinnerValueFactory(int initialValue) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, initialValue);
    }

    public abstract void setDealerSpeed();

    public abstract void setEngineFactorySpeed();

    public abstract void setAccessoryFactorySpeed();

    public abstract void setBodyFactorySpeed();
}
