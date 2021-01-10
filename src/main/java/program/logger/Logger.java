package program.logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
    private static Logger instance;

    private static final String filePath = "log.txt";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:ms:ns");

    private Logger()
    {
        try
        {
            File logFile = new File(filePath);
            logFile.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    synchronized public void writeLog(String logText)
    {
        try
        {
            Files.write(Paths.get(filePath), (logText + " " + dateFormatter.format(LocalDateTime.now()) + "\n").getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    synchronized public static Logger getInstance()
    {
        if(instance == null)
        {
            instance = new Logger();
        }
        return instance;
    }
}
