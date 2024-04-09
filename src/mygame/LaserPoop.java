package mygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import myengine.GameObject;
import myengine.GameTool;
import myengine.ObjectHandler;

public class LaserPoop extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 33L;
	public static final BufferedImage[] images;
	static
	{
		images = new BufferedImage[18];
		for (int i=0; i<18;++i)
		{
			images[i] = GameTool.readImage("beam\\"+(i-7)+".png");
		}
	}
	int nowImage;
	public LaserPoop(double x, double y, ObjectHandler handler) {
		super(x, y, images[0].getWidth(), images[0].getHeight(), handler);
		// TODO Auto-generated constructor stub
		nowImage = 0;
		GameTool.playSound("laser.wav", false);
	}
	double delayImage = 0;
	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		delayImage += perTime;
		if (delayImage > 0.06)
		{
			delayImage = 0;
			nowImage = (nowImage + 1) % 18;
			int orgwidth = width;
			width = images[nowImage].getWidth();
			height = images[nowImage].getHeight();
			if (nowImage == 17) handler.erase(this);
			x += (orgwidth - width) / 2;
		}
		
		if (getAvoider().getBounds().intersects(this.getBounds()))
		{
			getAvoider().applyDamage(Math.random() * 500.0 * perTime);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(images[nowImage], (int)x, (int)y, null);
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);
		
	}

}
