package battleship;

/**
 * Game UI interface
 * 
 * @date 20.01.2013
 * @author Christian Metz
 * @version 1.0
 */
public interface GameUI {

  /**
   * Display strike
   * 
   * @param x
   * @param y 
   */
  public void displayStrike(int x, int y);

  /**
   * Display missed shot
   * 
   * @param x
   * @param y 
   */
  public void displayMissed(int x, int y);

  /**
   * Display (sunken) ship, depending on its properties
   * 
   * @param ship 
   */
  public void displayShip(Ship ship);

}
