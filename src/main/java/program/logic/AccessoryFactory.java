package program.logic;

public class AccessoryFactory implements ParticleFactory {
    public CarParticle get() {
        return new Accessory();
    }
}