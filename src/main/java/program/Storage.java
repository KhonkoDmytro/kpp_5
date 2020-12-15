package program;


import java.util.ArrayList;

public class Storage<T> {
    //То для Діми (changedSinceLastCheck теж)
    Boolean changed;

    Integer maxSize;
    ArrayList<T> storage;

    public Storage(int maxSize)
    {
        this.maxSize = maxSize;
    }



    synchronized  T Get() throws StorageException
    {
        if(storage.isEmpty())  throw new StorageException();
        T res = storage.remove(storage.size() - 1);
        return res;
    }

    synchronized void Add(T particle) throws StorageException
    {
        if(storage.size() == maxSize) throw new StorageException();
        storage.add(particle);
    }

    synchronized boolean tryAdd()
    {
        return storage.size() < maxSize;
    }

    synchronized  boolean tryGet()
    {
        return storage.size() > 0;
    }

    synchronized Boolean changedSinceLastCheck()
    {
        Boolean res = changed;
        changed = false;
        return res;
    }
}
