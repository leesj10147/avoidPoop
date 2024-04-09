package myengine;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameTool
{
    public static Clip playSound(String file, boolean infinityLoop)
    {
    	Clip clip = null;
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(file)));
            if (infinityLoop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return clip;
    }
    public static BufferedImage readImage(String path)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(path));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
}