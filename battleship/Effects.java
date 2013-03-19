package battleship;

/**
 * Effects
 * 
 * @date 17.12.2012
 * @author Christian Metz
 * @version 1.0
 */
public class Effects
{

  // dir path to the sound files
  private final String SOUND_PATH = "../battleship/resources/sounds/";

  public void splash()
  {
    play("water_splash.mp3");
  }

  public void strike()
  {
    play("strike.mp3");
  }

  public void sinking()
  {
    play("sinking.mp3");
  }

  private void play(String filename)
  {
    Sound sound = new Sound(SOUND_PATH + filename);
    sound.playSound();
  }

}