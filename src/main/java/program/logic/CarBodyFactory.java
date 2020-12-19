package program.logic;


import program.logic.ParticleFactory;

public class CarBodyFactory implements ParticleFactory
{
    public CarBody get()
    {
        return new CarBody();
    }
}