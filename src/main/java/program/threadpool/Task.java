package program.threadpool;

public abstract class Task implements Runnable
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
    public abstract void run();
}
