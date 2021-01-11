package program.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        id = getAvailableId();
        creationTime = LocalDateTime.now();
        lastId = getAvailableId();
    }

    private static Integer getAvailableId() {
        synchronized (lastId) {
            return lastId++;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getCreationTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateFormatter.format(creationTime);
    }

    public String getType() {
        return "Car";
    }
}
