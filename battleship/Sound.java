package battleship;

import java.io.*;
import java.net.URL;
import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

/**
 * Sound Manager class
 * 
 * @date 3.12.2012
 * @author Christian Metz
 * @version 1.5
 */
public class Sound extends Thread
{

  private BufferedInputStream file = null;
  private Player player = null;

  public Sound(String filepath)
  {
    try {
      InputStream input = getClass().getResourceAsStream(filepath);
      file = new BufferedInputStream(input);
      player = new Player(file);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void playSound()
  {   
    start();
  }

  @Override
  public void run() 
  {
    try {
      player.play();
    } catch (JavaLayerException e) {
      System.out.println("Soundmanager error: " + e.getMessage());
    }
  }

/*
 
SwingWorker sw=new SwingWorker()
{
@Override
protected Object doInBackground() throws Exception
{
InputStream in=null;
.....abspielen....
return null;
}
};
sw.execute();
}
 */

}