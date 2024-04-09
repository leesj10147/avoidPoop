package myengine;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ConcurrentSkipListSet;

public class Game extends Canvas implements Runnable
{
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    private ObjectHandler handler;
    
    public Game(SceneInit initer)
    {
        this.addKeyListener(new KeyHandler());
        new Window(WIDTH, HEIGHT, this);
        this.createBufferStrategy(3);
        handler = new ObjectHandler(initer);
        new Thread(this).start();
    }
    public void run()
    {
    	double beginTime = System.nanoTime();
    	Thread.currentThread().setName("Main Game");
        while (true)
        {
            double endTime = System.nanoTime();
            double perTime = endTime - beginTime;
            beginTime = endTime;
            tick(perTime/1e9);
            render();
        }
    }

    private void tick(double perTime) 
    {
    	handler.tick(perTime); 
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        
        handler.render(g2d);

        g2d.dispose();
        bs.show();
    }
}
