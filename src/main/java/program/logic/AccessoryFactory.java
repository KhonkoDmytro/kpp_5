package program.logic;

import program.logic.ParticleFactory;

public class AccessoryFactory implements ParticleFactory
{
    public CarParticle get()
    {
        return new Accessory();
    }
}