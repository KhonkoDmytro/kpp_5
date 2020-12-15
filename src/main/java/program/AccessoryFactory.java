package program;

public class AccessoryFactory implements ParticleFactory
{
    public CarParticle get()
    {
        return new Accessory();
    }
}