package battleship;

/**
 * AirCarrier class
 * 
 * @date 20.01.2013
 * @author Christian Metz
 * @version 1.0
 */
public class AirCarrier extends Ship
{
  public AirCarrier(int x, int y, int length, boolean horizontal)
  {
    super(x, y, length, horizontal);
  }

  public String getType()
  {
    return "airCarrier";
  }
}