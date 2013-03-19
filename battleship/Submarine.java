package battleship;

/**
 * Submarine
 * 
 * @date 20.01.2013
 * @author Christian Metz
 * @version 1.0
 */
public class Submarine extends Ship
{
  public Submarine(int x, int y, int length, boolean horizontal)
  {
    super(x, y, length, horizontal);
  }

  public String getType()
  {
    return "submarine";
  }
}