package program.logic;

import program.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Storage<T extends Object> {
    Boolean changed;

    Integer maxSize;
    private final List<T> storage = Collections.synchronizedList(new ArrayList<>());



    public Storage(int maxSize) {
        this.maxSize = maxSize;
    }

    synchronized T get() {
        T res = storage.remove(0);
        return res;
    }

    synchronized void add(T particle) {
        storage.add(particle);
    }

    boolean tryAdd() {
        return storage.size() < maxSize;
    }

    boolean tryGet() {
        return storage.size() > 0;
    }

    synchronized Boolean changedSinceLastCheck() {
        Boolean res = changed;
        changed = false;
        return res;
    }

    public List<T> getStorage() {
        return storage;
    }
}
