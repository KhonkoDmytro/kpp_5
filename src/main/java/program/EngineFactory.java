package program;


public class EngineFactory implements ParticleFactory
{
    public CarParticle get()
    {
        return new Engine();
    }
}