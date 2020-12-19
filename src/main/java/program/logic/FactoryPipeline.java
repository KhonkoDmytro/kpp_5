package program.logic;

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

        carMounter.start();

    }

    Storage<Accessory> getAccessoryStorage()
    {
        return accessoryStorage;
    }

    Storage<Engine> getEngineStorage()
    {
        return engineStorage;
    }

    Storage<CarBody> getBodyStorage()
    {
        return bodyStorage;
    }

    Storage<Car> getCarStorage()
    {
        return carStorage;
    }
    
    public void terminate() {
        accessoryProducer.terminate();
        engineProducer.terminate();
        bodyProducer.terminate();
        carMounter.terminate();

        try
        {
            accessoryProducer.join();
            engineProducer.join();
            bodyProducer.join();
            carMounter.join();
        }
        catch (InterruptedException e)
        {

        }

    }



}
