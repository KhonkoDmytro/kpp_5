package program.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import program.entity.Engine;
import program.service.Service;

import java.time.LocalTime;
import java.util.List;

public abstract class ChartController {
    Service service = Service.getInstance();
    int lastUpdate = 0;
    int waitTime = 2000;
    boolean shouldClose = false;
    GraphThread th;
    XYChart.Series<Integer, Integer> series;
    @FXML
    LineChart<Integer, Integer> chart;

    class GraphThread extends Thread {
        public void run() {
            while (!shouldClose) {
                int items = getItems();
                int time = LocalTime.now().getSecond();
                Runnable update = new Runnable() {
                    @Override
                    public void run() {
                        if (time < lastUpdate) {
                            lastUpdate = time;
                            series.getData().clear();
                        }
                        series.getData().add(new XYChart.Data<>(LocalTime.now().getSecond(), items));
                        lastUpdate = time;
                    }
                };
                Platform.runLater(update);
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

    public abstract int getItems();

    public abstract void terminate();
}
