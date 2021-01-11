package program.logic;

import program.entity.Car;
import program.logger.Logger;

import java.util.ArrayList;

public class CarDealer extends Thread {
    int id;
    boolean shouldStop = false;
    long waitTime;
    Storage<Car> carStorage;
    ArrayList<Car> cars = new ArrayList<>();

    static int lastId = 0;
    synchronized static int getFreeId()
    {
        return ++lastId;
    }
    public CarDealer(Storage<Car> cs,
        long waitTime) {
        id = getFreeId();
        this.waitTime = waitTime;
        carStorage = cs;
        Logger.getInstance().writeLog("Create car dealer with id" + id);
    }

    public void run() {
        while (!shouldStop) {
            synchronized (Thread.currentThread()) {
                try {
                    while (!carStorage.tryGet()) {
                        ;
                    }
                    synchronized (carStorage) {
                        if (carStorage.tryGet()) {
                            cars.add(carStorage.get());
                        }
                    }
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    shouldStop = true;
                }

            }

        }
    }

    public void terminate() {
        shouldStop = true;
    }

    synchronized void setWaitTime(long milliseconds) {
        this.waitTime = milliseconds;
    }
}
