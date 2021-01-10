package program.threadpool;

import program.logger.Logger;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool extends Thread {
    private int size;
    long waitTime = 500;
    private ArrayList<Thread> threads;
    private BlockingQueue<Runnable> threadsQueue;
    ArrayList<Integer> availableThreads = new ArrayList<Integer>();

    boolean shouldStop = false;

    public void setShouldStop() {
        shouldStop = true;
    }

    class NotifyThreadEndedCommand implements Runnable
    {
        int threadIndex;

        public NotifyThreadEndedCommand(int index)
        {
            threadIndex = index;
        }

        @Override
        public void run() {
            try
            {
                threads.get(threadIndex).join();
                availableThreads.add(threadIndex);
            }
            catch(InterruptedException e)
            {
                shouldStop = true;
            }
        }
    }
    public ThreadPool() {
        this.size = 5;
        this.threadsQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<Thread>(size);
        for(int i = 0; i < size; i++)
        {
            availableThreads.add(i);
            threads.add(null);
        }
    }

//    public ThreadPool(int size) {
//        this.size = size;
//        this.threadsQueue = new LinkedBlockingQueue<Runnable>(size);
//        this.threads = new ArrayList<Thread>(size);
//        for (int i = 0; i < size; ++i) {
//            threads.set(i, (Thread)(new Thread()));
//            threads.get(i).start();
//        }
//    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public synchronized void enqueue(Runnable r) {
        Logger.getInstance().writeLog("zaishlo chy ne");
        synchronized (this.threadsQueue) {
            this.threadsQueue.add(r);
            Logger.getInstance().writeLog("added runnable to queue");
//            this.threadsQueue.notify();
            Logger.getInstance().writeLog("Notified after add(r)");
        }
    }

    public Runnable dequeue() {
        return this.threadsQueue.poll();
    }

    @Override
    public void run()
    {
        while(!shouldStop)
        {
            Runnable r = dequeue();
            if(r != null)
            {
                while(availableThreads.isEmpty()) {
                    synchronized (Thread.currentThread()) {
                        try {
                            Thread.currentThread().wait(waitTime);
                        } catch (InterruptedException e) {
                            shouldStop = true;
                        }
                    }
                }

                int i = availableThreads.get(0);

                threads.set(i, new Thread(r));
//                System.out.println("New thread started");
                threads.get(i).start();

                Thread waitThread = new Thread(new NotifyThreadEndedCommand(i));
                waitThread.start();

            }
            else
            {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(waitTime);
                    } catch (InterruptedException e) {
                        shouldStop = true;
                    }
                }
            }

        }
    }

    public void terminate()
    {
        shouldStop = true;
    }
// *** так приблизно мав би виглядати робітник, який переданий в ThreadPool (threads) *** //
//    public class Worker extends Thread
//    {
//        @Override
//        public void run()
//        {
//            Runnable r;
//            while(true)
//            {
//                synchronized (threadsQueue)
//                {
//                    while(threadsQueue.isEmpty())
//                    {
//                        try
//                        {
//                            threadsQueue.wait();
//                        }
//                        catch (InterruptedException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                    r = dequeue();
//                }
//                try
//                {
//                    r.run();
//                }
//                catch(Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
