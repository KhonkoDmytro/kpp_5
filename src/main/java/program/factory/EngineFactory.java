package program.factory;

import program.entity.CarParticle;
import program.entity.Engine;
import program.factory.ParticleFactory;

public class EngineFactory implements ParticleFactory {
    public CarParticle get() {
        return new Engine();
    }
}