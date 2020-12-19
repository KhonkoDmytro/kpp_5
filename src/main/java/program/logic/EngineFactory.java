package program.logic;

import program.logic.ParticleFactory;

public class EngineFactory implements ParticleFactory {
    public CarParticle get() {
        return new Engine();
    }
}