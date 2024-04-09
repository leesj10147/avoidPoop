package myengine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import mygame.Client;

public class KeyHandler extends KeyAdapter
{
    private static Set<Integer> input = new HashSet<>();
    @Override
    public void keyPressed(KeyEvent e)
    {
        input.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        input.remove(e.getKeyCode());
    }
    public static boolean isKeyPressed(int keyCode)
    {
    	if (Client.isClient)
    	{
    		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D)
    		{
    	        return input.contains(keyCode);		
    		}
    	}
    	else
    	{
    		if (keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_E || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
    		{
    	        return input.contains(keyCode);		
    		}
    	}
    	return false;
    }
}
