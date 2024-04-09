package mygame;

import java.io.IOException;
import java.io.ObjectInputStream;

import myengine.GameObject;
import myengine.ObjectHandler;

public class Receiver implements Runnable{
	ObjectInputStream in;
	ObjectHandler handler;
	public Receiver(ObjectInputStream in, ObjectHandler handler)
	{
		this.in = in;
		this.handler = handler;
		new Thread(this).start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				
				handler.netData.add((GameObject) in.readUnshared());
			} catch(Exception e)
			{
				System.out.println("상대방이 종료함");
				System.exit(0);
			}
		}
	}
	
}
