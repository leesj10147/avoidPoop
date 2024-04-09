package myengine;

import java.awt.*;
import java.io.Externalizable;
import java.io.Serializable;

import mygame.Avoider;
import mygame.Backgrounder;
import mygame.Client;
public abstract class GameObject implements Comparable<GameObject>, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 98765L;
	public transient ObjectHandler handler;
    public double x, y;
    public double velX, velY;
    public final int renderOrder;
    public int width, height;
    private final int hash;
    public GameObject(double x, double y, int width, int height, ObjectHandler handler)
    {
        this(x, y, width, height, handler, ObjectOrder.DEFAULT);
        
    }
    @Override
    public int hashCode()
    {
		return hash;
    	
    }
    private static int cnt = 0;
    public GameObject(double x, double y, int width, int height, ObjectHandler handler, int renderOrder)
    {
    	if (Client.isClient)
    		this.hash = (int)1e9 + (cnt++);
    	else
    		this.hash = cnt++;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.handler = handler;
        this.renderOrder = renderOrder;
        
    }

    public abstract void tick(double perTime);

    public abstract void render(Graphics2D g2d);

    public Rectangle getBounds()
    {
    	return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public int compareTo(GameObject o)
    {
        if (this.renderOrder == o.renderOrder) return this.hashCode() - o.hashCode();
        
        return this.renderOrder - o.renderOrder;
    }
    public Avoider getAvoider()
    {
    	for (GameObject o: handler.objects)
    	{
    		if (o instanceof Avoider) return (Avoider)o;
    	}
    	return null;
    }
    public Backgrounder getBack()
    {
    	for (GameObject o: handler.objects)
    	{
    		if (o instanceof Backgrounder) return (Backgrounder)o;
    	}
    	return null;
    }
}