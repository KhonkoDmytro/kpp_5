package program.threadpool;

import program.logger.Logger;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ThreadPool<T extends Thread> {
    private int size;
    private ArrayList<T> threads;
    private Queue<Runnable> threadsQueue;

    public ThreadPool() {
        this.size = 5;
        this.threadsQueue = new ConcurrentLinkedQueue<>();
        this.threads = new ArrayList<T>(size);
        for(int i = 0; i < threads.size(); ++i)
        {
            threads.set(i, (T)(new Thread()));
            threads.get(i).start();
        }
//        for (int i = 0; i < size; ++i) {
//            threads.set(i, (T) new Thread());
//            threads.get(i).start();
//        }
    }

    public ThreadPool(int size) {
        this.size = size;
        this.threadsQueue = new LinkedBlockingQueue<Runnable>(size);
        this.threads = new ArrayList<T>(size);
        for (int i = 0; i < size; ++i) {
            threads.set(i, (T)(new Thread()));
            threads.get(i).start();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void enqueue(Runnable r) {
        Logger.getInstance().writeLog("zaishlo chy ne");
        synchronized (this.threadsQueue) {
            this.threadsQueue.add(r);
            Logger.getInstance().writeLog("added runnable to queue");
            this.threadsQueue.notify();
            Logger.getInstance().writeLog("Notified after add(r)");
        }
    }

    public Runnable dequeue() {
        return this.threadsQueue.poll();
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
