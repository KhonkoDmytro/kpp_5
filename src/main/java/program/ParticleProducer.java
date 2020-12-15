package program;

public class ParticleProducer<T extends CarParticle> extends Thread{
    long waitTime; // in milliseconds
    boolean shouldStop = false;
    Storage<T> storage;
    ParticleFactory factory;
    public ParticleProducer(Storage<T> storage, ParticleFactory factory, long initialWaitTime)
    {
        this.storage = storage;
        this.factory = factory;
        waitTime = initialWaitTime;
    }

    void setWaitTime(long milliseconds)
    {
        waitTime = milliseconds;
    }

    public void run()
    {
        while(!shouldStop)
        {
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
            try
            {
                storage.Add((T) factory.get());
            }
            catch (StorageException e)
            {
                // Log шо алгоритм хуйня
                // чесно так заєбали ці ексепшени
            }
        }
    }

    void terminate()
    {
        shouldStop = true;
    }
}
