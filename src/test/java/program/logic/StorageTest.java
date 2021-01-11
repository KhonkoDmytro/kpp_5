package program.logic;

import org.junit.jupiter.api.Test;
import program.entity.Engine;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    Storage<Engine> storage = new Storage<>(2);

    Engine engine = new Engine();

    @Test
    void addTest() {
        storage.add(engine);
        assertEquals(engine, storage.get());
    }
}