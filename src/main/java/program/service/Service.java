package program.service;

<<<<<<< HEAD
import program.entity.Engine;
=======
import program.config.Configurator;
import program.logic.Engine;
>>>>>>> cb646b00b072de4e8309fc325d1d5142698c9de0
import program.logic.FactoryPipeline;

import java.io.File;
import java.util.List;

public class Service {
    private static Service instance;

    private  FactoryPipeline factoryPipeline;
    private Configurator configurator;

    private Service() {
        /* в конфігураторі 4 гетера для того шоб витягнути дані:
            configurator.GetCollectorsCount();
            configurator.GetDealersCount();
            configurator.GetProvidersCount();
            configurator.GetStorageSize();
        */
        factoryPipeline = new FactoryPipeline(3000,15);
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
    }

    public List<Engine> getEngines() {
        return factoryPipeline.getEngineStorage().getStorage();
    }

    public void setEngineFactorySpeed(int i) {
        factoryPipeline.setEngineProducerWaitTime(i);
    }
}
