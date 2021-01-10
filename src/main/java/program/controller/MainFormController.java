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
    private final Service service = Service.getInstance();
    long waitTime = 500;
    Boolean shouldClose = false;


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
        System.out.println("Changed");
    }

//    void SpinnerStart()
//    {
//        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
//            if (!"".equals(newValue)) {
//                System.out.println("spiinerrrr");
//            }
//        });
//    }

    class TestThread extends Thread {
        public void run()
        {
            while(!shouldClose) {
                engineList.clear();
                bodyList.clear();
                accessoryList.clear();
                carList.clear();
                engineList.addAll(FXCollections.observableArrayList(service.getEngines()));
                bodyList.addAll(FXCollections.observableArrayList(service.getBodies()));
                accessoryList.addAll(FXCollections.observableArrayList(service.getAccessories()));
                carList.addAll(FXCollections.observableArrayList(service.getCars()));
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(waitTime);
                    } catch (InterruptedException e) {
                        shouldClose = true;
                    }
                }
            }
        }
    }
    @FXML
    public void setList() {


//        List<Engine> test = service.getEngines();
//        if(test.size()==15) {
//            engineTable.setStyle("-fx-background-color: #f08080;");
//            for (TableColumn<Engine, ?> column : engineTable.getColumns()) {
//                column.setStyle("-fx-background-color:lightcoral");
//            }
//        }
        TestThread th = new TestThread();
        th.start();


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
