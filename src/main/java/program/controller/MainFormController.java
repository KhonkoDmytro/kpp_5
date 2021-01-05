package program.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import program.App;
import program.entity.Engine;
import program.service.Service;
import program.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainFormController extends View {
    Service service = Service.getInstance();

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
        if(test.size()==15) {
            engineTable.setStyle("-fx-background-color: #f08080;");
            for (TableColumn<Engine, ?> column : engineTable.getColumns()) {
                column.setStyle("-fx-background-color:lightcoral");
            }
        }
        ObservableList<Engine> list = FXCollections.observableArrayList(service.getEngines());
        engineTable.setItems(list);
    }
    @FXML
    public void changeToEngineStat() throws IOException {
        Scene secondScene = new Scene(App.loadFXML("chactEngineFactory"));

        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        newWindow.show();
    }
}
