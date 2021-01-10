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

    ThreadPool threadpool;

    private class CreateCar implements Runnable {
        public void run() {
            if (accessoryStorage.tryGet() &&
                engineStorage.tryGet() &&
                bodyStorage.tryGet()) {
                Logger.getInstance().writeLog("createcar1");
                Car newCar = new Car(engineStorage.get(),
                    bodyStorage.get(),
                    accessoryStorage.get());
                synchronized (carStorage) {
                    while (!carStorage.tryAdd())
                        ;
                    Logger.getInstance().writeLog("createcar2");
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

    public void run() {
        Logger.getInstance().writeLog("sooqa0");
        while (!shouldStop) {
            Logger.getInstance().writeLog("sooqa1");
            synchronized (accessoryStorage) {
                Logger.getInstance().writeLog("sooqa2");
                synchronized (engineStorage) {
                    Logger.getInstance().writeLog("sooqa3");
                    synchronized (bodyStorage) {
                        Logger.getInstance().writeLog("sooqa4");
                        Runnable r = new CreateCar();
                        threadpool.enqueue(r);
                        // command factory singleton threadpool, mvc
                        synchronized (Thread.currentThread()) {
                            try {
                                Logger.getInstance().writeLog("sooqa5");
                                Thread.currentThread().wait(waitTime);
                                r.run();
                            } catch (InterruptedException e) {
                                // Log huinya
                                Logger.getInstance().writeLog("sooqa6");
                                shouldStop = true;
                                threadpool.dequeue();
                            }
                        }
                    }
                }
            }
        }
    }

    public void terminate() {
        shouldStop = true;
        threadpool.dequeue();
    }

}
