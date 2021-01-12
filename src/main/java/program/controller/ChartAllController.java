package program.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import program.service.Service;
import java.time.LocalTime;

public class ChartAllController {
    @FXML
    public CategoryAxis xAxis;
    Service service = Service.getInstance();
    @FXML
    public BarChart<String, Integer> chart;

    private XYChart.Series<String, Integer> series;
    private GraphThread th = new GraphThread();
    private int lastUpdate = 0;
    private boolean shouldClose = false;

    public ChartAllController() {
        th.start();
    }

    public void initialize() {
        xAxis.setCategories(FXCollections.observableArrayList("Двигуни", "Кузови", "Аксесуари","Aвто"));
    }

    class GraphThread extends Thread {
        public void run() {
            while (!shouldClose) {
                int time = LocalTime.now().getMinute();
                Runnable update = new Runnable() {
                    @Override
                    public void run() {
                        if (time < lastUpdate) {
                            lastUpdate = time;
                            series.getData().clear();
                        }
                        series = new XYChart.Series<>();
                        series.setName("Кількість на " + time + "хв");
                        series.getData().add(new XYChart.Data<>("Двигуни", service.getEngines().size()));
                        series.getData().add(new XYChart.Data<>("Кузови", service.getBodies().size()));
                        series.getData().add(new XYChart.Data<>("Аксесуари", service.getAccessories().size()));
                        series.getData().add(new XYChart.Data<>("Авто", service.getCars().size()));
                        chart.getData().add(series);
                    }
                };
                Platform.runLater(update);
                synchronized (Thread.currentThread()) {
                    try {
                        int waitTime = 60000;
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

    public void terminate() {
        th.terminate();
    }
}
