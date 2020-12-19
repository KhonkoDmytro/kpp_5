package program.service;

import java.io.File;

public class Service {
    private static Service instance;

    private Service() {
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void openFile(File file) {

    }

}
