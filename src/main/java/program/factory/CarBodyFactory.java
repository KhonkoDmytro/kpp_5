package program.factory;

import program.entity.CarBody;

public class CarBodyFactory implements ParticleFactory {
    public CarBody get() {
        return new CarBody();
    }
}