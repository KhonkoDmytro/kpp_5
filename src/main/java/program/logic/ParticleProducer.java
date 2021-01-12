package program.logic;

import program.entity.CarParticle;
import program.factory.ParticleFactory;

public class ParticleProducer<T extends CarParticle> extends Thread {
    long waitTime;
    boolean shouldStop = false;
    Storage<T> storage;
    ParticleFactory factory;
    int generatedParticlesCount = 0;
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
                    shouldStop = true;
                    System.out.println("Unexpected error");
                }
            }

            synchronized (storage) {
                while (!storage.tryAdd())
                    ;
                storage.add((T) factory.get());
                generatedParticlesCount++;
            }

        }
    }

    //TODO на діма
    public int getNumberOfCreatedParticles() { return generatedParticlesCount; }
    public void terminate() {
        shouldStop = true;
        Thread.currentThread().interrupt();
    }
}
