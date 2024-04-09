package mygame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import myengine.Game;
import myengine.GameObject;
import myengine.GameTool;
import myengine.KeyHandler;
import myengine.ObjectHandler;

public class Avoider extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 756456435L;
	private double delayImage,delayTime,delaybomb,health;
	private int nowImage = 0;
	public boolean itemtaken,itempresent,isfinish,bombpushed;
	public String state;
	
	HealingPot hp;
	bomb bmb;
	Time time;
	int itemnum=0,itempos=0,bomb,t;
	private final Color[] col = new Color[]{Color.WHITE, Color.red, Color.orange, Color.green};
	public static final transient BufferedImage[] images;
	static
	{
		images = new BufferedImage[24];
		for (int i = 0; i<24; ++i)
		{
			String filePath = "avoider\\" + i + ".png";
			images[i] = GameTool.readImage(filePath);
		}
	}
	
	public Avoider(ObjectHandler handler) {
	
		super(10, 650, images[0].getWidth(), images[0].getHeight(), handler);
		delayImage = delayTime = delaybomb =  0;
		bomb = 1;
		health = 100;
		itempresent = itemtaken = isfinish= bombpushed = false;
		t=30;
		this.velX = 1000;
	}

	@Override
	public void tick(double perTime) {
		
	    if (KeyHandler.isKeyPressed(KeyEvent.VK_A)) this.x -= velX * perTime;
        if (KeyHandler.isKeyPressed(KeyEvent.VK_D)) this.x +=  velX * perTime;
        if (this.x < 0) this.x = 0;
        if (this.x + this.width >= Game.WIDTH) this.x = Game.WIDTH - this.width;
        
        delaybomb = Math.min(delaybomb + perTime, 1);
        if (KeyHandler.isKeyPressed(KeyEvent.VK_S) && delaybomb>=1)
            if(bomb>0)   
            {
               delaybomb=0;
               GameTool.playSound("explosion.wav", false);
               for(GameObject o: handler.objects)
               {
                  String classname =o.getClass().getName(); 
                  handler.insert(new bombAni(handler));
                  if(classname.equals("mygame.Poop") || classname.equals("mygame.LaserPoop") ||classname.equals("mygame.BigPoop")||classname.equals("mygame.GuidedPoop") )
                     handler.erase(o);
                     
               }
               bomb-=1;
            }
        
        delayImage += perTime;
        if (delayImage > 0.07)
		{
			delayImage = 0;
			nowImage = (nowImage + 1) % 24;
		}
        
        delayTime += perTime;
        if (delayTime > 1.5)
		{
        	
        	t-=1;
			delayTime = 0;
		}
        
     	Random rand = new Random(System.currentTimeMillis());
        if(itempresent == false)
        {
       
        	if(rand.nextFloat()< 0.001)
        	{
    
        		int type = rand.nextInt(3) + 1;
        		int pos = rand.nextInt(500)+1;
        		itemnum = type;
        		itempos = pos;
        	
        		switch(type)
        		{
        		case 1:
        			hp = new HealingPot(pos,this.y, handler);
        			handler.insert(hp);
        			break;
        		case 2:

        			bmb = new bomb(pos,this.y, handler);
        			handler.insert(bmb);
        			break;
        		case 3:

        			time = new Time(pos,this.y, handler);
        			handler.insert(time);
        			break;
        		}
        		
        		itempresent=true;
        		itemtaken= false;

        	}	
        }

        if(itempresent == true && itemtaken == false)
        	{
  
        	if(this.x >=(itempos-10) && this.x<=(itempos+10) )
        	{
        		GameTool.playSound("accept.wav", false);
        		switch(itemnum)
        		{
        		case 1:
        			hp.setItem(true);
        				health += rand.nextInt(30)+1;
        				if(health>100)health=100;
        			
        			break;
        		case 2:
        			bmb.setItem(true);
        			if(health>=0)
        				bomb++;
        			break;
        		case 3:
        			time.setItem(true);
        				t+=5;
        				if(t>30)t=30;
        			break;
        			
        			
        		}
        		itemtaken = true;
        		itempresent = false;
        	}
        }
        
        if(health<=0)
        {
        	isfinish = true;
        	state = "Attacker wins";
        	getBack().attackWin();
        }
        else if(t<=0)
        {
        	isfinish=true;
        	state = "Avoider wins";
        	getBack().avoideWin();
        }
        

	}
	void setbomb(boolean set)
	{
		bombpushed = set;
	}
	
	void applyDamage(double hp)
	{
		GameTool.playSound("ouch.wav", false);
		if(health-hp <=0)
			health=0;
		else
			health-=hp;

	}

	@Override
	public void render(Graphics2D g2d) {
		
		g2d.drawImage(images[nowImage], (int)x, (int)y, null);
		g2d.setColor(Color.green);
		
		g2d.drawRect((int)x, (int)y, width, height);
		
		g2d.setColor(Color.red);
		Rectangle redbar = new Rectangle(300,700,600,15);
		g2d.fill(redbar);
		

		g2d.setColor(Color.green);
		Rectangle greenbar = new Rectangle(300,700,(300/100)*(int)health,15);
		g2d.fill(greenbar);
		
		
		g2d.setColor(Color.black);
		g2d.drawString("health", 260, 710);
		g2d.drawString("time: " + String.valueOf(t) , 10, 710);
		g2d.drawString("bomb: " + String.valueOf(bomb) , 100, 710);
		
		if(bombpushed == true)
		{
			
		}
		
		if(isfinish==true)
		{

			g2d.setColor(Color.red);
			g2d.drawString(state, 250, 400);
		}

	}

}

