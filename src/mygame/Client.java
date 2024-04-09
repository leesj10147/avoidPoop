package mygame;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import myengine.Game;
import myengine.GameObject;
import myengine.KeyHandler;
import myengine.ObjectHandler;

public class Client{ // avoider
	static ObjectHandler Data;
	public static boolean isClient = false;
	public static void main(String args[]) {
		isClient = true;
		try {
			Socket socket = new Socket("localhost", 8000);
			System.out.println("Server connected!");
			Thread.sleep(1000);
			
			Game a = new Game((ObjectHandler handler)->{
				handler.insert(new Avoider(handler));
				handler.insert(new Backgrounder(handler));
				Data = handler;
			});
			DataCommunicationClient th1 = new DataCommunicationClient(Data, socket);
			th1.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

