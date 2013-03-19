package battleship;

/**
 * Battleship
 * operates Battleship
 * 
 * @date 17.12.2012
 * @author Christian Metz
 * @version 3.2
 */
public class Battleship
{

  public static void main(String[] args)
  {
    Battleship game = new Battleship(10);
  }

  public Battleship(int size)
  {
    GameField field = new GameField(size);
  }

}