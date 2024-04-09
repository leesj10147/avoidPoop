package mygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import myengine.Game;
import myengine.GameObject;
import myengine.GameTool;
import myengine.ObjectHandler;

public class Poop extends GameObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 111L;
	public static final BufferedImage image = GameTool.readImage("poop.png");
	public Poop(double x, double y, ObjectHandler handler) {
		super(x, y, image.getWidth(), image.getHeight(), handler);
		// TODO Auto-generated constructor stub
		velY = 500;
		GameTool.playSound("poop.wav", false);
	}

	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		this.y += velY * perTime;
		if (this.y >= Game.HEIGHT) handler.erase(this);
		if (getAvoider().getBounds().intersects(this.getBounds()))
		{
			getAvoider().applyDamage(Math.random() * 30);
			handler.erase(this);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(image, (int)x, (int)y, null);
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);
	}

}
