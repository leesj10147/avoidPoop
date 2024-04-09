package myengine;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

import mygame.*;
public class ObjectHandler
{
	public ConcurrentSkipListSet<GameObject> objects = new ConcurrentSkipListSet<GameObject>();
    private Queue<GameObject> adds = new LinkedList<>();
    private Queue<GameObject> removes = new LinkedList<>();
    
    public ObjectHandler(SceneInit initer)
    {
    	initer.init(this);
    }
    
    public ConcurrentLinkedQueue<GameObject> netData = new ConcurrentLinkedQueue<>();
    public void tick(double perTime)
    {
        objects.forEach(x->x.tick(perTime));
        while (!adds.isEmpty()) 
        {
        	GameObject o = adds.poll();
        	if (Server.isServer && (o instanceof Attacker || o instanceof BigPoop || o instanceof GuidedPoop || o instanceof LaserPoop || o instanceof Poop || o instanceof Backgrounder))
        		objects.add(o);
        	else if (Client.isClient && (o instanceof Avoider || o instanceof bomb || o instanceof bombAni || o instanceof Time || o instanceof HealingPot || o instanceof Backgrounder))
        		objects.add(o);
        }
        while (!removes.isEmpty()) 
        {
        	GameObject tmp = removes.poll();
        	objects.remove(tmp);
        	Deleter.deltHash.add(tmp.hashCode());
        	if (Client.isClient)
        	{
        		DataCommunicationClient.dels.add(new Deleter(tmp));
        	}
        	else
        	{
        		DataCommunication.dels.add(new Deleter(tmp));
        	}
        }
        
        while(!netData.isEmpty())
        {
        	GameObject o = netData.poll();
        	if (o instanceof Deleter)
        	{
        		Deleter.deltHash.add(o.hashCode());
        		objects.remove(((Deleter)o).del);
        		continue;
        	}
        	if (Deleter.deltHash.contains(o.hashCode())) continue;
        	o.handler = this;
        	objects.remove(o);
        	objects.add(o);
        }
    }

    public void render(Graphics2D g2d)
    {
        objects.forEach(i -> i.render(g2d));
    }

    public void insert(GameObject object)
    {
        this.adds.add(object);
    }

    public void erase(GameObject object)
    {
        this.removes.add(object);
    }
}
