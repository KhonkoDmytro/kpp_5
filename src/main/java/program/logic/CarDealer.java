package program.logic;

import program.entity.Car;

import java.util.ArrayList;

public class CarDealer extends Thread {
    boolean shouldStop = false;
    long waitTime;
    Storage<Car> carStorage;
    ArrayList<Car> cars = new ArrayList<>();
    public CarDealer(Storage<Car> cs,
                     long waitTime)
    {
        this.waitTime = 5000;
        carStorage = cs;
    }

    public void run()
    {
        while(!shouldStop)
        {
            synchronized (Thread.currentThread())
            {
                try
                {
                    while(!carStorage.tryGet())
                    {
                        Thread.currentThread().wait(100);
                    }
                    //synchronized (carStorage)
       //             {
                        if(carStorage.tryGet())
                        {
                            cars.add(carStorage.get());
                            Thread.currentThread().wait(waitTime);
                        }
                    //}
                }
                catch(InterruptedException e)
                {
                    shouldStop = true;
                }

            }

        }
    }

    public void terminate()
    {
        shouldStop = true;
    }

    synchronized void setWaitTime(long milliseconds)
    {
        this.waitTime = milliseconds;
    }
}
