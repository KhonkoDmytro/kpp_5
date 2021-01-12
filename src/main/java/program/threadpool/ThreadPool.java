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

    class NotifyThreadEndedCommand implements Runnable {
        int threadIndex;

        public NotifyThreadEndedCommand(int index) {
            threadIndex = index;
        }

        @Override
        public void run() {
            try {
                threads.get(threadIndex).join();
                availableThreads.add(threadIndex);
            } catch (InterruptedException e) {
                shouldStop = true;
            }
            //System.out.println("Thread " + threadIndex + " ended");

        }
    }

    public ThreadPool() {
        this.size = 5;
        this.threadsQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<Thread>(size);
        for (int i = 0; i < size; i++) {
            availableThreads.add(i);
            threads.add(null);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public synchronized void enqueue(Runnable r) {
        synchronized (this.threadsQueue) {
            this.threadsQueue.add(r);
            Logger.getInstance().writeLog("Added runnable to queue");
            Logger.getInstance().writeLog("Notified after add(r)");
        }
    }

    public Runnable dequeue() {
        return this.threadsQueue.poll();
    }

    @Override
    public void run() {
        while (!shouldStop) {
            Runnable r = dequeue();
            if (r != null) {
                while (availableThreads.isEmpty()) {
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
                threads.get(i).start();
               // System.out.println("Thread " + i + " started");

                Thread waitThread = new Thread(new NotifyThreadEndedCommand(i));
                waitThread.start();

            } else {
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

    public void terminate() {
        shouldStop = true;
    }
}
