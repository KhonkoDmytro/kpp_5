package program;


public class CarBodyFactory implements ParticleFactory
{
    public CarBody get()
    {
        return new CarBody();
    }
}