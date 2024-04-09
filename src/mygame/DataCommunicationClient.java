package mygame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import myengine.GameObject;
import myengine.ObjectHandler;

public class DataCommunicationClient extends Thread {
	ObjectInputStream fromServer;
	ObjectOutputStream toServer;
	ObjectHandler Data;
	Socket socket;
	public static Queue<Deleter> dels = new ConcurrentLinkedQueue<>();
	public DataCommunicationClient (ObjectHandler Data, Socket socket) {
		this.Data = Data;
		this.socket = socket;
		
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Receiver(fromServer, Data);
	}
	
	public void run() {
		while(true) {
			try {
				//ConcurrentSkipListSet<GameObject> sd = new ConcurrentSkipListSet<GameObject>();
				for (GameObject o: Data.objects)
				{
					if (o instanceof Avoider || o instanceof bomb || o instanceof bombAni || o instanceof Time || o instanceof HealingPot)
						toServer.writeUnshared(o);
				}
				while(!dels.isEmpty())
				{
					toServer.writeUnshared(dels.poll());
				}
				toServer.flush();
				Thread.sleep(10);
			} catch (Exception e)
			{
				System.out.println("상대방이 종료함");
				System.exit(0);
			}
		}
	}
}