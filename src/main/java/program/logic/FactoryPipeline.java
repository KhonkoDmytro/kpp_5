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
import java.util.List;

public class FactoryPipeline extends Thread {
    int dealersCount = 1;
    int producersCount = 1;
    int mountersCount = 1;
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;
    Storage<Car> carStorage;

    List<ParticleProducer<Engine>> engineProducers;
    List<ParticleProducer<Accessory>> accessoryProducers;
    List<ParticleProducer<CarBody>> bodyProducers;

    List<CarMounter> carMounters;

    ArrayList<CarDealer> dealers = new ArrayList<>();

    ThreadPool threadpool;

    public FactoryPipeline(int initialTime,
                           int maxSize,
                           int dealersCount,
                           int producersCount,
                           int mountersCount) {
        Logger.getInstance().writeLog("Starting program");
        accessoryStorage = new Storage<>(maxSize);
        engineStorage = new Storage<>(maxSize);
        bodyStorage = new Storage<>(maxSize);
        carStorage = new Storage<>(maxSize);
        this.dealersCount = dealersCount;
        this.mountersCount = mountersCount;
        this.producersCount = producersCount;
        threadpool = new ThreadPool();

        accessoryProducers = new ArrayList<>(); //new ParticleProducer<>(accessoryStorage, new AccessoryFactory(), initialTime);
        engineProducers = new ArrayList<>(); //new ParticleProducer<>(engineStorage, new EngineFactory(), initialTime);
        bodyProducers = new ArrayList<>(); //new ParticleProducer<>(bodyStorage, new CarBodyFactory(), initialTime);
        carMounters = new ArrayList<>(); //;
        for (int i = 0; i < dealersCount; i++) {
            dealers.add(new CarDealer(carStorage, initialTime));
        }
        for (int i = 0; i < producersCount; i++)
        {
            accessoryProducers.add(new ParticleProducer<>(accessoryStorage, new AccessoryFactory(), initialTime));
            engineProducers.add(new ParticleProducer<>(engineStorage, new EngineFactory(), initialTime));
            bodyProducers.add(new ParticleProducer<>(bodyStorage, new CarBodyFactory(), initialTime));
        }
        for(int i = 0; i < mountersCount; i++)
        {
            carMounters.add(new CarMounter(accessoryStorage,
                    engineStorage,
                    bodyStorage,
                    carStorage,
                    initialTime,
                    threadpool));
        }
        CarMounter.init();
    }

    public void run() {
        for (CarMounter d : carMounters) {
            d.start();
        }
        for (ParticleProducer d : accessoryProducers) {
            d.start();
        }
        for (ParticleProducer d : engineProducers) {
            d.start();
        }
        for (ParticleProducer d : bodyProducers) {
            d.start();
        }
        for (CarDealer d : dealers) {
            d.start();
        }
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
        for (ParticleProducer accessoryProducer : accessoryProducers) {
            accessoryProducer.setWaitTime(milliseconds);
        }
    }

    public void setEngineProducerWaitTime(long milliseconds) {
        for (ParticleProducer engineProducer : engineProducers) {
            engineProducer.setWaitTime(milliseconds);
        }
    }

    public void setCarBodyProducerWaitTime(long milliseconds) {
        for (ParticleProducer bodyProducer : bodyProducers) {
            bodyProducer.setWaitTime(milliseconds);
        }
    }

    public int getAccessoryProducerCreatedParticlesCount() {
        int all = 0;
        for(ParticleProducer p : accessoryProducers)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public int getEngineProducerCreatedParticlesCount() {
        int all = 0;
        for(ParticleProducer p : engineProducers)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public int getCarBodyProducerCreatedParticlesCount() {
        int all = 0;
        for(ParticleProducer p : bodyProducers)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public void setDealersWaitTime(long milliseconds) {
        for (CarDealer d : dealers) {
            d.setWaitTime(milliseconds);
        }
    }

    public void terminate() {
//        accessoryProducer.terminate();
//        engineProducer.terminate();
//        bodyProducer.terminate();
//        carMounter.terminate();

        for(ParticleProducer p : bodyProducers)
        {
            p.terminate();
        }
        for(ParticleProducer p : engineProducers)
        {
            p.terminate();
        }
        for(ParticleProducer p : accessoryProducers)
        {
            p.terminate();
        }
        for(CarMounter m : carMounters)
        {
            m.terminate();
        }
        for (CarDealer d : dealers) {
            d.terminate();
        }
        try {
            for(ParticleProducer p : bodyProducers)
            {
                p.join();
            }
            for(ParticleProducer p : engineProducers)
            {
                p.join();
            }
            for(ParticleProducer p : accessoryProducers)
            {
                p.join();
            }
            for(CarMounter m : carMounters)
            {
                m.join();
            }
            for (CarDealer d : dealers) {
                d.join();
            }
        } catch (InterruptedException e) {

        }

    }

}
