package program.threadpool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool<T extends Thread> {
    private int size;
    private ArrayList<T> threads;
    private BlockingQueue<Runnable> threadsQueue;

    public ThreadPool() {
    }

    public ThreadPool(int size) {
        this.size = size;
        this.threadsQueue = new LinkedBlockingQueue<Runnable>(size);
        this.threads = new ArrayList<T>(size);
        for (int i = 0; i < size; ++i) {
            threads.set(i, (T) new Thread());
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
        synchronized (this.threadsQueue) {
            this.threadsQueue.add(r);
            this.threadsQueue.notify();
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
