package myengine;

import javax.swing.*;

import mygame.Client;

public class Window
{
    public Window(int width, int height, Game game)
    {
    	game.setSize(width, height);
    	String str = "Server";
    	if (Client.isClient) str = "Client";
        JFrame frame = new JFrame(str);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.setFocusable(true);
        game.requestFocus();
    }
}
