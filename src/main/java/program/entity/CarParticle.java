package program.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CarParticle {
    final LocalTime creationTime;
    Integer id;

    static Integer lastId = 1;

    protected CarParticle() {
        creationTime = LocalTime.now();
        id = getAvailableId();
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
//        return Integer.toString(creationTime.();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateFormatter.format(creationTime);
    }

}
