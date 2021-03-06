package program.entity;

import program.logger.Logger;

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
        Logger.getInstance().writeLog("Creating new car particle of type " + this.getClass().getName());

    }

    private synchronized static Integer getAvailableId() {
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

}
