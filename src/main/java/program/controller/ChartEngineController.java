package program.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import program.entity.Engine;
import program.service.Service;
import java.time.LocalTime;
import java.util.List;

public class ChartEngineController extends ChartController {

    public ChartEngineController() {
        series = new XYChart.Series<>();
        th = new GraphThread();
    }

    @FXML
    public void initialize() {
        chart.getData().add(series);
        series.setName("Двигуни");
        th.start();
    }

    @Override
    public void terminate() {
        th.terminate();
    }

    public int getItems() {
        return service.getEnginesCount();
    }
}
