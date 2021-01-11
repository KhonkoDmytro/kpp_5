package program.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import program.entity.Accessory;

import java.util.List;

public class ChartBodyController extends ChartController {
    public ChartBodyController() {
        series = new XYChart.Series<>();
        th = new GraphThread();
    }

    @FXML
    public void initialize() {
        chart.getData().add(series);
        series.setName("Аксесуари");
        th.start();
    }

    @Override
    public void terminate() {
        th.terminate();
    }

    public List<Accessory> getItems() {
        return service.getAccessories();
    }
}
