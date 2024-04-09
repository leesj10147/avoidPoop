package mygame;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import myengine.Game;
import myengine.GameObject;
import myengine.KeyHandler;
import myengine.ObjectHandler;

public class Server { // attacker
	static ObjectHandler Data;
	public static boolean isServer = false;
	public static void main(String[] args) {
		isServer = true;
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			System.out.println("Waiting for Client");
			Socket socket = serverSocket.accept();
			System.out.println("Client connected at " + new Date());
			Thread.sleep(1000);
			
			Game a = new Game((ObjectHandler handler)->{
				handler.insert(new Attacker(handler));
				handler.insert(new Backgrounder(handler));
				Data = handler; // Game 함수 내에서 계속 돌면서 실행될것이라 예상하였으나, 전혀 전달되지않음.
			});
			DataCommunication th1 = new DataCommunication(Data, socket);
			
			th1.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}


