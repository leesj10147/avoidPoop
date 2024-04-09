package mygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import myengine.Game;
import myengine.GameObject;
import myengine.GameTool;
import myengine.ObjectHandler;

public class GuidedPoop extends GameObject{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 15L;
	public static final BufferedImage[] images;
	static
	{
		images = new BufferedImage[24];
		for (int i= 0 ; i<24;++i)
		{
			images[i] = GameTool.readImage("guided\\" + i + ".png");
		}
	}
	int nowImage;
	double vel;
	public GuidedPoop(double x, double y, ObjectHandler handler) {
		super(x, y, images[0].getWidth(), images[0].getHeight(), handler);
		// TODO Auto-generated constructor stub
		nowImage = 0;
		
		GameTool.playSound("guided.wav", false);
		
		vel = 300;
	}
	double delayImage = 0;
	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		delayImage += perTime;
		if (delayImage > 0.04)
		{
			delayImage = 0;
			nowImage = (nowImage + 1) % 24;
			int orgwidth = width;
			width = images[nowImage].getWidth();
			height = images[nowImage].getHeight();
			x += (orgwidth - width) / 2;
		}
		
		double dx = getAvoider().x - x;
		double dy = Math.max(getAvoider().y - y, 70);
		double mag = Math.sqrt(dx * dx + dy * dy);
		velX = dx *  vel / mag;
		velY = dy * vel / mag;
		
		this.x += velX * perTime;
		this.y += velY * perTime;
		
		if (this.y >= Game.HEIGHT) handler.erase(this);
		//
		if (getAvoider().getBounds().intersects(this.getBounds()))
		{
			getAvoider().applyDamage(Math.random() * 40);
			handler.erase(this);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		AffineTransform old = g2d.getTransform();
		AffineTransform nw = new AffineTransform(old);
		//
		nw.rotate(Math.atan2(-velX, velY), x + width / 2, y + height / 2);
		g2d.setTransform(nw);
		g2d.drawImage(images[nowImage], (int)x, (int)y, null);
		g2d.setTransform(old);
		
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);
	}

}
