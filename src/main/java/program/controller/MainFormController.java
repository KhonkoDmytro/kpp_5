package program.controller;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import program.App;
import program.service.Service;
import program.view.View;
import java.io.File;
import java.io.IOException;

public class MainFormController extends View {
    private final Service service = Service.getInstance();
    long waitTime = 500;
    Boolean shouldClose = false;
    ViewThread th;

    public MainFormController() {
        th = new ViewThread();
        th.start();
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

    @Override
    public void setEngineFactorySpeed() {
        int value = engineSpeed.getValue();
        service.setEngineFactorySpeed(5000 / value);
    }

    class ViewThread extends Thread {
        public void run() {
            while (!shouldClose) {
                engineList.clear();
                engineList.addAll(FXCollections.observableArrayList(service.getEngines()));
                bodyList.clear();
                bodyList.addAll(FXCollections.observableArrayList(service.getBodies()));
                accessoryList.clear();
                accessoryList.addAll(FXCollections.observableArrayList(service.getAccessories()));
                carList.clear();
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

        void terminate() {
            shouldClose = true;
        }
    }

    @Override
    public void setAccessoryFactorySpeed() {
        int value = accessorySpeed.getValue();
        service.setAccessoryFactorySpeed(5000 / value);
    }

    @Override
    public void setBodyFactorySpeed() {
        int value = shapeSpeed.getValue();
        service.setBodyFactorySpeed(5000 / value);
    }

    @Override
    public void setDealerSpeed() {
        int value = shapeSpeed.getValue();
        service.setDealerSpeed(20000 / value);
    }

    public void terminate() {
        service.terminate();
        th.terminate();
        System.exit(0);
    }

    @FXML
    public void changeToEngineStat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chartEngineFactory.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Графік кількості двигунів");
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ((ChartController) fxmlLoader.getController()).terminate();
            }
        });
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    @FXML
    public void changeToAccessoryStat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chartAccessoryFactory.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Графік кількості аксесуарів");
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ((ChartController) fxmlLoader.getController()).terminate();
            }
        });
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    @FXML
    public void changeToBodyStat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chartBodyFactory.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Графік кількості кузовів");
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ((ChartController) fxmlLoader.getController()).terminate();
            }
        });
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    @FXML
    public void changeToAllStat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("chartAllFactories.fxml"));
        Scene secondScene = new Scene(fxmlLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("Графік кількості всіх деталей");
        newWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ((ChartAllController) fxmlLoader.getController()).terminate();
            }
        });
        newWindow.setScene(secondScene);
        newWindow.show();
    }
}
