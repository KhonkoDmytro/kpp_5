package program.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import program.App;
import program.entity.Engine;
import program.service.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class ChartEngineController {
    Service service = Service.getInstance();
    int lastUpdate = 0;
    XYChart.Series<Integer,Integer> series;

    public ChartEngineController() {
        series = new XYChart.Series<>();
    }
    @FXML
    LineChart<Integer,Integer> chart;

    @FXML
    public void test() {

        List<Engine> engines = service.getEngines();
        series.setName("Engines");
        int time = LocalTime.now().getSecond();
        if(time < lastUpdate) {
            lastUpdate = time;
            series.getData().clear();
        }
        series.getData().add(new XYChart.Data<>(LocalTime.now().getSecond(), engines.size()));
        chart.getData().add(series);
        lastUpdate = time;
    }
}
