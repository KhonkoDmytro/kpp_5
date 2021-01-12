package program.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;

public class ChartAutoContorller extends ChartController {


    public ChartAutoContorller() {
        series = new XYChart.Series<>();
        th = new GraphThread();
    }

    @FXML
    public void initialize() {
        chart.getData().add(series);
        series.setName("Автомобілі");
        th.start();
    }

    @Override
    public void terminate() {
        th.terminate();
    }

    public int getItems() {
        return service.getCarCount();
    }
}
