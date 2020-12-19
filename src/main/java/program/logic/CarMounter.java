package program.logic;

import program.threadpool.ThreadPool;

public class CarMounter extends Thread{
    Storage<Accessory> accessoryStorage;
    Storage<Engine> engineStorage;
    Storage<CarBody> bodyStorage;

    Storage<Car> carStorage;

    boolean shouldStop = false;
    long waitTime;

    ThreadPool threadpool;

    private class CreateCar implements Runnable{
        public void run(){
            if(     accessoryStorage.tryGet() &&
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
        }
    }
    public CarMounter(Storage<Accessory> as,
                      Storage<Engine> es,
                      Storage<CarBody> bs,
                      Storage<Car> cs,
                      long waitTime,
                      ThreadPool th)
    {
        accessoryStorage = as;
        engineStorage = es;
        bodyStorage = bs;
        carStorage = cs;

        this.waitTime = waitTime;
        threadpool = th;
    }

    public void run()
    {
        while(shouldStop) {
            synchronized (accessoryStorage) {
                synchronized (engineStorage) {
                    synchronized (bodyStorage) {
                        threadpool.enqueue(new CreateCar());
                        // command factory singleton threadpool, mvc
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
