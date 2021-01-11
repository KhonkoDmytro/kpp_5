package program.logic;

import program.entity.Accessory;
import program.entity.Car;
import program.entity.CarBody;
import program.entity.Engine;
import program.logger.Logger;
import program.threadpool.ThreadPool;

public class CarMounter extends Thread {
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;

    Storage<Car> carStorage;

    boolean shouldStop = false;
    long waitTime;

    static ThreadPool threadpool;
    boolean first = true;
    private class CreateCar implements Runnable {
        public void run() {
            if (accessoryStorage.tryGet() &&
                engineStorage.tryGet() &&
                bodyStorage.tryGet()) {
                Car newCar = new Car(engineStorage.get(),
                    bodyStorage.get(),
                    accessoryStorage.get());
                synchronized (carStorage) {
                    while (!carStorage.tryAdd())
                        ;
                    carStorage.add(newCar);
                }

            }
        }
    }

    public CarMounter(Storage<Accessory> as,
        Storage<Engine> es,
        Storage<CarBody> bs,
        Storage<Car> cs,
        long waitTime,
        ThreadPool th) {
        accessoryStorage = as;
        engineStorage = es;
        bodyStorage = bs;
        carStorage = cs;

        this.waitTime = waitTime;
        threadpool = th;
    }

    static public void init()
    {
        threadpool.start();
    }
    public void run() {
        Logger.getInstance().writeLog("Starting car mounter");
        while (!shouldStop) {
            Runnable r = new CreateCar();
            threadpool.enqueue(r);
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    shouldStop = true;
                }
            }
        }
    }

    public void terminate() {
        shouldStop = true;
        threadpool.dequeue();
        Logger.getInstance().writeLog("Terminated car mounter");
        threadpool.setShouldStop();
    }

}
