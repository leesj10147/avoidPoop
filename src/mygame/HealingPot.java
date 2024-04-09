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

public class HealingPot extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 17L;
	private boolean itemtaken;
	public static final BufferedImage image = GameTool.readImage("pots.png");

	public HealingPot(double x, double y, ObjectHandler handler) {
		super(x, y, image.getWidth(), image.getHeight(), handler);
		itemtaken = false;
		GameTool.playSound("ping.wav", false);
	}
	private double angle = 0;
	@Override
	public void tick(double perTime) {
		
	if(itemtaken==true)
		handler.erase(this);

	
	}
	
	void setItem(boolean set)
	{
		itemtaken = set;
	}
	
	

	@Override
	public void render(Graphics2D g2d) {
		
		g2d.drawImage(image, (int)x, (int)y, null);
		g2d.setColor(Color.green);
		g2d.drawRect((int)x, (int)y, width, height);

}}