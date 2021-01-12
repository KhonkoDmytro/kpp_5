package program.service;

import program.entity.Accessory;
import program.entity.Car;
import program.entity.CarBody;
import program.entity.Engine;
import program.config.Configurator;
import program.logger.Logger;
import program.logic.FactoryPipeline;

import java.io.File;
import java.util.List;

public class Service {
    private static Service instance;

    private final Logger logger = Logger.getInstance();
    private FactoryPipeline factoryPipeline;
    private final Configurator configurator = new Configurator();

    private Service() {
        factoryPipeline =
            new FactoryPipeline(3000, configurator.GetStorageSize(), configurator.GetDealersCount(), configurator.GetProvidersCount(), configurator.GetCollectorsCount());
        factoryPipeline.start();
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void openFile(File file) {
        configurator.LoadConfigFromFile(file);
        factoryPipeline.terminate();
    }

    public List<Engine> getEngines() {
        logger.writeLog("received engines");
        return factoryPipeline.getEngineStorage().getStorage();
    }

    public List<Accessory> getAccessories() {
        return factoryPipeline.getAccessoryStorage().getStorage();
    }

    public List<CarBody> getBodies() {
        return factoryPipeline.getBodyStorage().getStorage();
    }

    public List<Car> getCars() {
        return factoryPipeline.getCarStorage().getStorage();
    }

    public void setEngineFactorySpeed(int i) {
        factoryPipeline.setEngineProducerWaitTime(i);
    }

    public void setAccessoryFactorySpeed(int i) {
        factoryPipeline.setAccessoryProducerWaitTime(i);
    }

    public void setBodyFactorySpeed(int i) {
        factoryPipeline.setCarBodyProducerWaitTime(i);
    }

    public void terminate() {
        factoryPipeline.terminate();
    }

    public void setDealerSpeed(int i) {
        factoryPipeline.setDealersWaitTime(i);
    }

    public int getEnginesCount() {
        return factoryPipeline.getEngineProducerCreatedParticlesCount();
    }

    public int getAccessoryCount() {
        return factoryPipeline.getAccessoryProducerCreatedParticlesCount();
    }

    public int getBodyCount() {
        return factoryPipeline.getCarBodyProducerCreatedParticlesCount();
    }

    public int getCarCount() {
        return factoryPipeline.getCarsCount();
    }
}
