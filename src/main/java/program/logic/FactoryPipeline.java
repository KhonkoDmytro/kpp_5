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

import java.util.ArrayList;

public class FactoryPipeline extends Thread {

    //TODO: Діма зчитай з конфігу
    final int dealersCount = 1;
    
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;
    Storage<Car> carStorage;

    ParticleProducer<Accessory> accessoryProducer;
    ParticleProducer<Engine> engineProducer;
    ParticleProducer<CarBody> bodyProducer;

    CarMounter carMounter;

    ArrayList<CarDealer> dealers = new ArrayList<>();


    ThreadPool threadpool;

    public FactoryPipeline(int initialTime, int maxSize) {
        accessoryStorage = new Storage<>(maxSize);
        engineStorage = new Storage<>(maxSize);
        bodyStorage = new Storage<>(maxSize);
        carStorage = new Storage<>(maxSize);

        threadpool = new ThreadPool();

        accessoryProducer = new ParticleProducer<>(accessoryStorage, new AccessoryFactory(), initialTime);
        engineProducer = new ParticleProducer<>(engineStorage, new EngineFactory(), initialTime);
        bodyProducer = new ParticleProducer<>(bodyStorage, new CarBodyFactory(), initialTime);
        carMounter = new CarMounter(accessoryStorage,
            engineStorage,
            bodyStorage,
            carStorage,
            initialTime,
            threadpool);

        for(int i = 0; i < dealersCount; i++)
        {
            dealers.add(new CarDealer(carStorage, initialTime));
        }

    }

    public void run() {
        accessoryProducer.start();
        engineProducer.start();
        bodyProducer.start();
        Logger.getInstance().writeLog("carMounter.start()");
        carMounter.start();

        for(CarDealer d : dealers)
        {
            d.start();
        }
    }

    public Storage<Accessory> getAccessoryStorage() {
        System.out.println(accessoryStorage.getStorage().size());
        return accessoryStorage;
    }

    public Storage<Engine> getEngineStorage() {
        System.out.println(engineStorage.getStorage().size());
        return engineStorage;
    }

    public Storage<CarBody> getBodyStorage() {
        System.out.println(bodyStorage.getStorage().size());
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

    //TODO: Діма підключи то до слайдера
    public void setDealersWaitTime(long milliseconds)
    {
        for(CarDealer d : dealers)
        {
            d.setWaitTime(milliseconds);
        }
    }
    public void terminate() {
        accessoryProducer.terminate();
        engineProducer.terminate();
        bodyProducer.terminate();
        carMounter.terminate();
        for(CarDealer d : dealers)
        {
            d.terminate();
        }
        try {
            accessoryProducer.join();
            engineProducer.join();
            bodyProducer.join();
            carMounter.join();
            for(CarDealer d : dealers)
            {
                d.join();
            }
        } catch (InterruptedException e) {

        }

    }

}
