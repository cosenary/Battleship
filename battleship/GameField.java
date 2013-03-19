package battleship;

import java.util.Arrays;
import java.util.Random;

/**
 * Battleship GameField
 * 
 * @date 17.12.2012
 * @author Christian Metz
 * @version 5.0
 */
public class GameField
{

  // field size
  private final int size;

  // statistics
  private int sunkenShips = 0;
  private int strikeCount = 0;
  private int shotCount = 0;
  private int shipCount = 0;

  // container for all field objects
  private Field[][] gameField;
  private final int[] shipTypes = { 1, 2, 3 };

  // unit feature classes
  private final GameUI gui = new GameGUI(this); // new GameConsole(this);
  // private final Highscore highscore = new Highscore();

  /**
   * Custom constructor
   * 
   * @param size 
   */
  public GameField(int size)
  {
        System.out.println("########### hello world");
    // prepeare field
    this.size = size;
    
    gameField = new Field[size][size];
    
    for (Field[] row : gameField)
    {
      Arrays.fill(row, new SingleField());
    }
    
    // generate random ships
    generateShips();
    debuging();
  }

  /**
   * Is game solved
   * 
   * @return 
   */
  public boolean solved()
  {
    if (sunkenShips == shipCount)
    {
      System.out.printf("===> Congratulations! You destroyed all %d ships with %d shots!\n", shipCount, shotCount);
      // Add highscore entry
      // highscore.addScore("Tester", calculatePoints());
      // int playerScore = highscore.getScoreByName("Tester");
      // output gamer's score
      // System.out.printf("===> Congratulations! You destroyed all %d ships with %d shots!\n", playerScore);
      return true;
    }
    return false;
  }

  /**
   * Shoot on position
   * 
   * @param x
   * @param y
   * @return 
   */
  public boolean shoot(int x, int y)
  {
    System.out.println("SIZE: " + this.size);
    // get field object on the given position
    Field field = getField(x, y);
    
    // increase shot count
    shotCount++;
    
    // check whether the coordinates are in the game field
    if (x >= size || y >= size)
    {
      System.out.println("===> Coordinates out of range!");
      return false;
    }
    
    // check whther ship exists
    if (field instanceof Ship)
    {
      // fetch ship object from array and typcast it
      Ship ship = (Ship) field;
      
      // ship already sunken?
      if (ship.sunken())
      {
        return false;
      }
      
      // shoot on ship
      ship.shot();
      
      // strike
      gui.displayStrike(x, y);
      strikeCount++;
      
      if (ship.sunken())
      {
        gui.displayShip(ship);
        sunkenShips++;
      }
      
      return true;
    } else {
      // empty field
      SingleField singleField = (SingleField) field;
      singleField.shot();
    }
    
    // no strike
    gui.displayMissed(x, y);
    return false;
  }

  /**
   * Generate ships
   * 
   * default number of ships: 10
   */
  private void generateShips()
  {    
    shipCount = 10;
    
    for (int i = 0; i < shipCount; i++)
    {
      createShip();
    }
  }

  /**
   * Create single ship
   */
  private void createShip()
  {
    Random random = new Random();
    int x, y;
    boolean horizontal;
    int maxLength = 4;
    int length = random.nextInt(maxLength - 2 + 1) + 2;
    
    // 1x4 // 2x3 //3x2 // 4x1
    
    boolean shipCreated;
    
    do {
      x = random.nextInt(size);
      y = random.nextInt(size);
      horizontal = random.nextBoolean();
      
      // try to place ship at random position
      shipCreated = setShip(x, y, length, horizontal);
    } while (!shipCreated);
  }

  /**
   * Set ship
   * 
   * @param x
   * @param y
   * @param length
   * @param horizontal
   * @return 
   */
  private boolean setShip(int x, int y, int length, boolean horizontal)
  {
    if (!isOccupied(x, y) && checkFieldLength(x, y, length, horizontal) && checkFieldArea(x, y, length, horizontal))
    {
      Ship ship;
      
      // get shipt type according to its length
      switch (length)
      {
        case 2:
          // boat
          ship = new Boat(x, y, length, horizontal);
          break;
        case 3:
          if (x < 4) // test case
          {
            // destroyer
            ship = new Destroyer(x, y, length, horizontal);
          } else {
            // submarine
            ship = new Submarine(x, y, length, horizontal);
          }
          break;
        case 4:
          // cruiser
          ship = new Cruiser(x, y, length, horizontal);
          break;
        case 5:
          // air carrier
          ship = new AirCarrier(x, y, length, horizontal);
          break;
        default:
          // boat
          ship = new Boat(x, y, length, horizontal);
          break;
      }
      
      // built ship with given length
      for (int i = 0; i < length; i++)
      {
        if (horizontal)
        {
          setField(x + i, y, ship);
        } else {
          setField(x, y + i, ship);
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Check whether the ship length is possible at this position
   * 
   * @param x
   * @param y
   * @param length
   * @param horizontal
   * @return 
   */
  private boolean checkFieldLength(int x, int y, int length, boolean horizontal)
  {
    // are start cooridnates out of range
    if (x < size && y < size)
    {
      // check ship length from this position
      if (horizontal)
      {
        if ((x + length - 1) < size)
        {
          return true;
        }
      } else {
        if ((y + length - 1) < size)
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check if the area arround a position is clear
   * 
   * @param x
   * @param y
   * @param length
   * @param horizontal
   * @return 
   */
  private boolean checkFieldArea(int x, int y, int length, boolean horizontal)
  {
    // check bevor and behind
    if (horizontal)
    {
      // check left head and tail horizontally
      if (!(getField(x + length, y) instanceof SingleField) || !(getField(x - 1, y) instanceof SingleField))
      {
        return false;
      }
    } else {
      // check left head and tail vertically
      if (!(getField(x, y + length) instanceof SingleField) || !(getField(x, y - 1) instanceof SingleField))
      {
        return false;
      }
    }
    
    // check above and beyond
    for (int i = 0; i < length; i++)
    {
      if (horizontal)
      {
        // check left and right side horizontally
        if (!(getField(x + i, y - 1) instanceof SingleField) || !(getField(x + i, y + 1) instanceof SingleField))
        {
          return false;
        }
      } else {
        // check left and right side vertically
        if (!(getField(x - 1, y + i) instanceof SingleField) || !(getField(x + 1, y + i) instanceof SingleField))
        {
          return false;
        }
      }
    }

    // all tests passed
    return true;
  }

  /**
   * Check if position isn't already occupied
   * 
   * @param x
   * @param y
   * @return 
   */
  private boolean isOccupied(int x, int y)
  {
    return !(getField(x, y) instanceof SingleField);
  }

  /**
   * Get field
   * 
   * @param x
   * @param y
   * @return 
   */
  private Field getField(int x, int y)
  {
    if (x >= 0 && y >= 0 && x < size && y < size)
    {
      return gameField[y][x];
    }
    // out of game field (necessary to place ships on the field's border)
    return new SingleField();
  }

  /**
   * Set field
   * 
   * @param x
   * @param y
   * @param elem 
   */
  private void setField(int x, int y, Field elem)
  {
    if (x >= 0 && y >= 0 && x < size && y < size)
    {
      gameField[y][x] = elem;
    }
  }

  // move away
  public String statistic()
  {
    String output = "";
    output += String.format("+======= Game statistic =======+\n");
    output += String.format("| ⛵  Destroyed:     %-10s |\n", sunkenShips);
    output += String.format("| 🎯  Strikes:       %-10s |\n", strikeCount);
    output += String.format("| 🔫  Shots:         %-10s |\n", shotCount);
    output += String.format("+==============================+\n");
    return output;
  }

  private int calculatePoints()
  {
    if ((shotCount - strikeCount + 1) > 0)
    {
      return (strikeCount * 4) / (shotCount - strikeCount + 1);
    }
    return 0;
  }

  /**
   * For debugging purposes, to see where the ships are located
   */
  private void debuging()
  {
    String output = "";
    for (int row = 0; row < size; row++)
    {
      for (int col = 0; col < size; col++)
      {
        if (!(getField(col, row) instanceof SingleField))
        {
          output += (getField(col, row) instanceof Ship) ? "X" : "S";
        } else {
          output += "#";
        }
        output += " ";
      }
      output += "\n";
    }
    System.out.println(output);
  }

}