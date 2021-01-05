package program.factory;

import program.entity.Accessory;
import program.entity.CarParticle;

public class AccessoryFactory implements ParticleFactory {
    public CarParticle get() {
        return new Accessory();
    }
}