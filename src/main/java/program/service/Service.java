package program.service;

import program.entity.Engine;
import program.logic.FactoryPipeline;

import java.io.File;
import java.util.List;

public class Service {
    private static Service instance;

    private  FactoryPipeline factoryPipeline;

    private Service() {
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

    }

    public List<Engine> getEngines() {
        return factoryPipeline.getEngineStorage().getStorage();
    }

    public void setEngineFactorySpeed(int i) {
        factoryPipeline.setEngineProducerWaitTime(i);
    }
}
