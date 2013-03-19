package battleship;

/**
 * An empty field
 * 
 * @date 5.01.2013
 * @author Christian Metz
 * @version 1.0
 */
public class SingleField extends Field
{
  public SingleField()
  {
    super(0, 0, 1, true);
  }

  public boolean sunken()
  {
    return false;
  }

  public void shot()
  {
    // play water splash sound
    effects.splash();
  }
}