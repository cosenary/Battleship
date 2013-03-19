package battleship;

/**
 * Field
 * 
 * @date 17.12.2012
 * @author Christian Metz
 * @version 1.3
 */
public abstract class Field
{

  private int length = 1;
  private boolean horizontal = false;
  private int x;
  private int y;

  // Sound effect manager
  protected final Effects effects = new Effects();

  public Field(int x, int y, int length, boolean horizontal)
  {
    if (length > 0)
    {
      this.length = length;
    }
    // set ship options
    this.horizontal = horizontal;
    this.x = x;
    this.y = y;
  }

  public int getLength()
  {
    return length;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean isHorizontal()
  {
    return horizontal;
  }

  abstract public boolean sunken();

  abstract public void shot();

}