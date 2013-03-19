package battleship;

/**
 * This class represents a ship
 * 
 * @date 12.12.2012
 * @author Christian Metz
 * @version 1.4
 */
public abstract class Ship extends Field
{
  private int intactParts;
  private boolean sunken = false;
 
  public Ship(int x, int y, int length, boolean horizontal)
  {
    super(x, y, length, horizontal);
    intactParts = this.getLength();
  }

  public boolean sunken()
  {
    if (intactParts == 0)
    {
      // sinking
      if (!sunken)
      {
        // no already sunken, thus play sound
        effects.sinking();
      }
      sunken = true;
      return true;
    }
    return false;
  }

  public void shot()
  {
    intactParts--;
    // play sound
    effects.strike();
  }

  abstract public String getType();
}