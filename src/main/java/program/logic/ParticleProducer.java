package program.logic;

import program.entity.CarParticle;
import program.factory.ParticleFactory;

public class ParticleProducer<T extends CarParticle> extends Thread {
    long waitTime; // in milliseconds
    boolean shouldStop = false;
    Storage<T> storage;
    ParticleFactory factory;

    public ParticleProducer(Storage<T> storage, ParticleFactory factory, long initialWaitTime) {
        this.storage = storage;
        this.factory = factory;
        waitTime = initialWaitTime;
    }

    void setWaitTime(long milliseconds) {
        waitTime = milliseconds;
    }

    public void run() {
        while (!shouldStop) {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    // Log
                    shouldStop = true;
                }
            }

            synchronized (storage) {
                while (!storage.tryAdd())
                    ;
                storage.add((T) factory.get());
            }

        }
    }

    void terminate() {
        shouldStop = true;
    }
}
