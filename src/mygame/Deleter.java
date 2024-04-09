package mygame;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import myengine.GameObject;

public class Deleter extends GameObject implements Serializable{
	public GameObject del;
	public static Set<Integer> deltHash = new HashSet<Integer>();
	public Deleter(GameObject del) {
		super(0, 0, 0, 0, null);
		// TODO Auto-generated constructor stub
		this.del = del;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3253489793879L;

	@Override
	public void tick(double perTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
	

}
