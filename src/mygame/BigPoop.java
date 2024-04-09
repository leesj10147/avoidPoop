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

public class BigPoop extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;
	public static final BufferedImage image = GameTool.readImage("bigpoop.png");
	private double angleVel;
	public BigPoop(double x, double y, ObjectHandler handler) {
		super(x, y, image.getWidth(), image.getHeight(), handler);
		velY = 1000;
		angleVel = 10;
		GameTool.playSound("bigpoop.wav", false);
	}
	private double angle = 0;
	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		this.y += velY * perTime;
		if (this.y >= Game.HEIGHT) handler.erase(this);
		angle += perTime * angleVel;
		//
		if (getAvoider().getBounds().intersects(this.getBounds()))
		{
			getAvoider().applyDamage(Math.random() * 60);
			handler.erase(this);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		AffineTransform old = g2d.getTransform();
		AffineTransform nw = new AffineTransform(old);
		nw.rotate(angle, x + width/2, y + height/2);
		g2d.setTransform(nw);
		g2d.drawImage(image, (int)x, (int)y, null);
		g2d.setTransform(old);
		
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);
	}

}
