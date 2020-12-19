package program.threadpool;

public class Task implements Runnable
{
    private int id;
    private String info;

    public Task(int id)
    {
        this.id = id;
    }
    public Task(int id, String info)
    {
        this.id = id;
        this.info = info;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    @Override
    public void run()
    {
        //Завданнями для ThreadPool є запити на створення нових машин (від контролера складу готових виробів)
        // тобто треба унаслідувати Task і перевизначити run()
        System.out.println("Task #" + this.id + ": " + this.info);
    }
}
