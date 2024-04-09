package mygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import myengine.Game;
import myengine.GameObject;
import myengine.GameTool;
import myengine.KeyHandler;
import myengine.ObjectHandler;

public class Attacker extends GameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5123131L;

	private int big, guided, laser;

	private double delayPoop, delayBig, delayGuided, delayLaser; // 3 where except init
	public static final transient BufferedImage[] images;
	static
	{
		images = new BufferedImage[24];
		for (int i = 0; i<24; ++i)
		{
			String filePath = "attacker\\" + i + ".png";
			images[i] = GameTool.readImage(filePath);
		}
	}
	private double delayImage;
	private int nowImage = 0;
	private final Color[] col = new Color[]{Color.WHITE, Color.red, Color.orange, Color.green};
	public Attacker(ObjectHandler handler) {
		super(10, 10, images[0].getWidth(), images[0].getHeight(), handler);
		big = guided = laser = 3;
		delayPoop = delayBig = delayGuided = delayLaser = 0;
		delayImage = 0;
		
		this.velX = 1000;
	}
	@Override
	public void tick(double perTime) {
        if (KeyHandler.isKeyPressed(KeyEvent.VK_LEFT)) this.x -= velX * perTime;
        if (KeyHandler.isKeyPressed(KeyEvent.VK_RIGHT)) this.x +=  velX * perTime;
        if (this.x < 0) this.x = 0;
        if (this.x + this.width >= Game.WIDTH) this.x = Game.WIDTH - this.width;
        delayPoop  = Math.min(delayPoop + perTime, 0.3);
        delayBig = Math.min(delayBig + perTime, 1.5);
        delayGuided = Math.min(delayGuided + perTime, 1.5);
        delayLaser = Math.min(delayLaser + perTime, 3);
        delayImage += perTime;
        if (delayImage > 0.03)
		{
			delayImage = 0;
			nowImage = (nowImage + 1) % 24;
		}
        if (KeyHandler.isKeyPressed(KeyEvent.VK_SPACE) && delayPoop >= 0.3)//Poop
        {
        	delayPoop = 0;
        	handler.insert(new Poop(x + width/2 - Poop.image.getWidth()/2, y + 10, handler));
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_Q) && big != 0 && delayBig >= 1.5)
        {
        	delayBig = 0;
        	--big;
        	handler.insert(new BigPoop(x + width/2 - BigPoop.image.getWidth() / 2, y + 10, handler));
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_W) && guided != 0 && delayGuided >= 1.5)
        {
        	delayGuided = 0;
        	--guided;
        	handler.insert(new GuidedPoop(x + width / 2 - GuidedPoop.images[0].getWidth() / 2, y + 10, handler));
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_E) && laser != 0 && delayLaser >= 3)
        {
        	delayLaser = 0;
        	--laser;
        	handler.insert(new LaserPoop(x + width/2 - LaserPoop.images[0].getWidth() / 2, y + 10, handler));
        }
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.green);
		g2d.fillRect(0, 0, (int) (20 * delayPoop/0.3), 10);
		g2d.setColor(col[big]);
		g2d.fillRect(0, 11,  (int)(20 * delayBig/1.5), 10);
		g2d.setColor(col[guided]);
		g2d.fillRect(0, 22, (int)(20 * delayGuided/1.5), 10);
		g2d.setColor(col[laser]);
		g2d.fillRect(0, 33, (int)(20 * delayLaser/3), 10);
		
		g2d.drawImage(images[nowImage], (int)x, (int)y, null);
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);
	}

}
