package mygame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import myengine.GameObject;
import myengine.ObjectHandler;

public class DataCommunication extends Thread {
	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	ObjectHandler Data;
	Socket socket;
	public static Queue<Deleter> dels = new ConcurrentLinkedQueue<>();
	public DataCommunication(ObjectHandler Data, Socket socket) {
		this.Data = Data;
		this.socket = socket;
		
		
		try {
			fromClient = new ObjectInputStream(socket.getInputStream());
			toClient = new ObjectOutputStream(socket.getOutputStream()); // 데이터 보냄
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 역직렬화
		
		new Receiver(fromClient, Data);
	}
	
	public void run() {
		while(true) {
			try {
				for (GameObject o: Data.objects)
				{
					if (o instanceof Attacker || o instanceof BigPoop || o instanceof GuidedPoop || o instanceof LaserPoop || o instanceof Poop)
						toClient.writeUnshared(o);
				}
				while(!dels.isEmpty())
				{
					toClient.writeUnshared(dels.poll());
				}
				toClient.flush();
				Thread.sleep(10);
			} catch(Exception e)
			{
				System.out.println("상대방이 종료함");
				System.exit(0);
				
			}
		}
	}
}