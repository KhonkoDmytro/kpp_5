package program.entity;

import java.time.LocalDateTime;

public class Car {
    Engine engine;
    CarBody body;
    Accessory accessory;

    Integer id;
    LocalDateTime creationTime;

    static Integer lastId = 1;

    public Car(Engine e, CarBody b, Accessory a) {
        engine = e;
        body = b;
        accessory = a;
        creationTime = LocalDateTime.now();
        lastId = getAvailableId();
    }

    private static Integer getAvailableId() {
        synchronized (lastId) {
            return lastId++;
        }
    }

    Integer getId() {
        return id;
    }

    LocalDateTime getCreationTime() {
        return creationTime;
    }
}
