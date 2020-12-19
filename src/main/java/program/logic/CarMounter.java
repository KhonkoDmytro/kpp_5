package program.logic;

public class CarMounter extends Thread{
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;

    Storage<Car> carStorage;

    boolean shouldStop = false;
    long waitTime;
    public CarMounter(Storage<Accessory> as,
                      Storage<Engine> es,
                      Storage<CarBody> bs,
                      Storage<Car> cs,
                      long waitTime)
    {
        accessoryStorage = as;
        engineStorage = es;
        bodyStorage = bs;
        carStorage = cs;

        this.waitTime = waitTime;
    }

    public void run()
    {
        while(shouldStop) {
            synchronized (accessoryStorage) {
                synchronized (engineStorage) {
                    synchronized (bodyStorage) {
                        if(accessoryStorage.tryGet() &&
                        engineStorage.tryGet() &&
                        bodyStorage.tryGet())
                        {
                            Car newCar = new Car(engineStorage.get(),
                                    bodyStorage.get(),
                                    accessoryStorage.get());
                            synchronized (carStorage)
                            {
                                while(!carStorage.tryAdd());

                                carStorage.add(newCar);
                            }

                        }
                        synchronized (Thread.currentThread())
                        {
                            try
                            {
                                Thread.currentThread().wait(waitTime);
                            }
                            catch (InterruptedException e) {
                                // Log huinya
                                shouldStop = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void terminate()
    {
        shouldStop = true;
    }

}
