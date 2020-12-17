package Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
    private static final Logger instance = new Logger();

    private static final String filePath;

    private static final DateTimeFormatter dateFormatter;

    static
    {
        filePath = "log.txt";
        dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:ms:ns");
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

    private Logger()
    { }

    public synchronized void writeLog(String logText)
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

    public static Logger getInstance()
    {
        return instance;
    }
}
