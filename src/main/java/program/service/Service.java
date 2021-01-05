package program.service;

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
        /* в конфігураторі 4 гетера для того шоб витягнути дані:
            configurator.GetCollectorsCount();
            configurator.GetDealersCount();
            configurator.GetProvidersCount();
            configurator.GetStorageSize();
        */
        factoryPipeline = new FactoryPipeline(3000,configurator.GetStorageSize());
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
//        factoryPipeline.terminate();
//        factoryPipeline = new FactoryPipeline(3000,configurator.GetStorageSize());
    }

    public List<Engine> getEngines() {
        logger.writeLog("received engines");
        return factoryPipeline.getEngineStorage().getStorage();
    }

    public void setEngineFactorySpeed(int i) {
        factoryPipeline.setEngineProducerWaitTime(i);
    }
}
