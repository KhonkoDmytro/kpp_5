package program.logic;

import program.entity.Accessory;
import program.entity.Car;
import program.entity.CarBody;
import program.entity.Engine;
import program.factory.AccessoryFactory;
import program.factory.CarBodyFactory;
import program.factory.EngineFactory;
import program.logger.Logger;
import program.threadpool.ThreadPool;

public class FactoryPipeline extends Thread {
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;
    Storage<Car> carStorage;

    ParticleProducer<Accessory> accessoryProducer;
    ParticleProducer<Engine> engineProducer;
    ParticleProducer<CarBody> bodyProducer;

    CarMounter carMounter;

    ThreadPool threadpool;

    public FactoryPipeline(int initialTime, int maxSize) {
        accessoryStorage = new Storage<>(maxSize);
        engineStorage = new Storage<>(maxSize);
        bodyStorage = new Storage<>(maxSize);
        carStorage = new Storage<>(maxSize);

        threadpool = new ThreadPool<>();

        accessoryProducer = new ParticleProducer<>(accessoryStorage, new AccessoryFactory(), initialTime);
        engineProducer = new ParticleProducer<>(engineStorage, new EngineFactory(), initialTime);
        bodyProducer = new ParticleProducer<>(bodyStorage, new CarBodyFactory(), initialTime);
        carMounter = new CarMounter(accessoryStorage,
            engineStorage,
            bodyStorage,
            carStorage,
            initialTime,
            threadpool);

    }

    public void run() {
        accessoryProducer.start();
        engineProducer.start();
        bodyProducer.start();
        Logger.getInstance().writeLog("carMounter.start()");
        carMounter.start();

    }

    public Storage<Accessory> getAccessoryStorage() {
        return accessoryStorage;
    }

    public Storage<Engine> getEngineStorage() {
        return engineStorage;
    }

    public Storage<CarBody> getBodyStorage() {
        return bodyStorage;
    }

    public Storage<Car> getCarStorage() {
        return carStorage;
    }

    public void setAccessoryProducerWaitTime(long milliseconds) {
        accessoryProducer.setWaitTime(milliseconds);
    }

    public void setEngineProducerWaitTime(long milliseconds) {
        engineProducer.setWaitTime(milliseconds);
    }

    public void setCarBodyProducerWaitTime(long milliseconds) {
        bodyProducer.setWaitTime(milliseconds);
    }

    public void terminate() {
        accessoryProducer.terminate();
        engineProducer.terminate();
        bodyProducer.terminate();
        carMounter.terminate();
        try {
            accessoryProducer.join();
            engineProducer.join();
            bodyProducer.join();
            carMounter.join();
        } catch (InterruptedException e) {

        }

    }

}
