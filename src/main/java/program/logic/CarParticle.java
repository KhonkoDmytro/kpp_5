package program.logic;

import java.time.LocalDateTime;

public class CarParticle {
    final LocalDateTime creationTime;
    Integer id;

    static Integer lastId = 1;

    protected CarParticle() {
        creationTime = LocalDateTime.now();
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

}
