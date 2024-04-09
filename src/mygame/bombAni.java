package mygame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import myengine.Game;
import myengine.GameObject;
import myengine.GameTool;
import myengine.ObjectHandler;

public class bombAni extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 13L;
	static BufferedImage[] image;
	static
	{
		image = new BufferedImage[18];
		for (int i=0; i<18;++i)
		{
			image[i] = GameTool.readImage("bomb\\" + i + ".jpg");
		}
	}
	public bombAni(ObjectHandler handler) {
		super((Game.WIDTH - image[0].getWidth()) / 2, (Game.HEIGHT - image[0].getHeight()) / 2, 0, 0, handler);
		// TODO Auto-generated constructor stub
	}
	double delayimage = 0;
	int nowimage = 0;
	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		delayimage += perTime;
		if (delayimage > 0.06)
		{
			delayimage = 0;
			
			int orgwidth = image[nowimage].getWidth();
			int orgheight = image[nowimage].getHeight();
			nowimage = (nowimage + 1) % 18;
			
			x += (orgwidth - image[nowimage].getWidth()) / 2;
			y += (orgheight - image[nowimage].getHeight()) / 2;
			
			if (nowimage == 17) handler.erase(this);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(image[nowimage], (int)x, (int)y, null);
	}

}
