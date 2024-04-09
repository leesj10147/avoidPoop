package mygame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import myengine.*;

public class Backgrounder extends GameObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 987867564539L;
	transient BufferedImage at, av;
	transient Clip backclip;
	public Backgrounder(ObjectHandler handler) {
		super(0, 0, 0, 0, handler, 10000);
		// TODO Auto-generated constructor stub
		this.backclip = GameTool.playSound("background.wav", true);
		at = GameTool.readImage("attackwin.jpg");
		av = GameTool.readImage("avoidwin.png");
	}
	boolean avwin = false, atwin = false;
	
	double after = 0;
	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
    	if (KeyHandler.isKeyPressed(KeyEvent.VK_ESCAPE)) System.exit(0);
	}
	public void attackWin()
	{
		if (!atwin && !avwin)
		{
			backclip.stop();
			GameTool.playSound("attackwin.wav", false); 
			atwin = true;
		}
	}
	public void avoideWin()
	{
		if (!atwin && !avwin)
		{
			backclip.stop();
			GameTool.playSound("avoidwin.wav", false);
			avwin = true;
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if (atwin)
			g2d.drawImage(at, 0, 0, at.getWidth(), at.getHeight(), null);
		else if (avwin)
			g2d.drawImage(av, 0, 0, av.getWidth(), av.getHeight(), null);
	}
	
}
