package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Game's console playground
 * 
 * @deprecated
 * @author christianmetz
 */
public class GameConsole implements GameUI
{

  private final int SIZE = 10;

  private String[][] gameField;
  private final GameField field;

  // console output -> movie it away
  private String[] icons;
  private String[] macIcons = { "💧", "💦", "🔥", "⛵", "🚩", "🏊" };
  private String[] winIcons = { "~", "o", "X", "+", "^", "S" };
  private final String coordinateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public GameConsole(GameField field)
  {
    // set GameField -> to use its shoot() method
    this.field = field;
    gameField = new String[SIZE][SIZE];
    
    // set icons according to the OS
    //setIcons();
    icons = winIcons;
    
    // flood the tank with water
    for (String[] row : gameField)
    {
      Arrays.fill(row, icons[0]);
    }
    
    // display welcome ASCII art
    welcome();
    
    displayField();
    
    // register input listener
    inputListener();
  }

  /**
   * Display skrike shot
   * 
   * @param x
   * @param y 
   */
  public void displayStrike(int x, int y)
  {
    setField(x, y, icons[2]);
  }

  /**
   * Display missed shot
   * 
   * @param x
   * @param y
   */
  public void displayMissed(int x, int y)
  {
    setField(x, y, icons[1]);
  }

  /**
   * Display sunken ship
   * 
   * @param ship
   */
  public void displayShip(Ship ship)
  {
    for (int i = 0; i < ship.getLength(); i++)
    {
      // flag ship as sinking
      if (ship.isHorizontal())
      {
        setField(ship.getX() + i, ship.getY(), icons[4]);
      } else {
        setField(ship.getX(), ship.getY() + i, icons[4]);
      }
    }
    System.out.println("===> Ship is sinking!\n");
  }

  /**
   * Set icon on specified field position
   * 
   * @param x
   * @param y
   */
  private void setField(int x, int y, String icon)
  {
    gameField[y][x] = icon;
  }

  private boolean shoot(int x, char y)
  {
    // convert y char into coordinate integer
    return shoot(x, coordinateChars.indexOf(Character.toUpperCase(y)));
  }

  private boolean shoot(int x, int y)
  {
    return field.shoot(x, y);
  }

  /**
   * Return graphical char gamefield
   * 
   * @return gamefield string
   */
  private void displayField()
  {
    // create new field
    String output = "  ";
    for (int i = 0; i < SIZE; i++)
    {
      output += i + ((i < SIZE - 1) ? " " : " --> x\n");
    }
    
    for (int row = 0; row < SIZE; row++)
    {
      output += coordinateChars.charAt(row) + " ";
      for (int col = 0; col < SIZE; col++)
      {
        output += gameField[row][col] + " ";
      }
      output += "\n";
    }
    System.out.println(output);
  }

  public void welcome()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("                                     |__\n");
    sb.append("                                     |\\/\n");
    sb.append("                                     ---\n");
    sb.append("                                     / | [\n");
    sb.append("                              !      | |||\n");
    sb.append("                            _/|     _/|-++'\n");
    sb.append("                        +  +--|    |--|--|_ |-\n");
    sb.append("                     { /|__|  |/\\__|  |--- |||__/\\\n");
    sb.append("                    +---------------___[}-_===_.'____                 /\\\n");
    sb.append("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _\n");
    sb.append(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7\n");
    sb.append("|                   Java Battleship by Christian Metz V.3.7                 /\n");
    sb.append(" \\_________________________________________________________________________|\n\n");
    sb.append("===> Enter your coordinates: x,y\n");
    
    System.out.println(sb.toString());
  }

  private void inputListener()
  {
    while (true) // (!game.solved())
    {
      try
      {
        // read one line from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        
//        switch (input)
//        {
//          case "stats":
//          case "statistic":
//            // display gamer's statistic
//            System.out.print("\n" + game.statistic() + "\n");
//            continue;
//          case "highscore":
//            // display current highscore
//            Highscore score = new Highscore();
//            System.out.print("\n" + score + "\n");
//            continue;
//        }
        
        // make sure only one character was entered
        if (input.length() < 3)
        {
          System.out.println("Please enter your coordinates: y,x");
          continue;
        }
        
        String[] coordinates = input.split(",");
        
        // guess character (trim whitespace)
        char y = coordinates[0].trim().charAt(0);
        int x = Integer.parseInt(coordinates[1].trim());
        
        // shoot on field
        shoot(x, y);
        
        // display gamefield
        displayField();
      } catch (IOException e) {
        System.out.println("IOException: " + e);
      }
    }
  }

  private void setIcons()
  {
    if (system().equals("mac"))
    {
      // mac
      icons = macIcons;
    } else if (system().equals("win")) {
      // windows
      icons = winIcons;
    } else {
      System.out.println("===> Your system is not supported :(");
      System.exit(0);
    }
  }

  private String system()
  {
    String os = System.getProperty("os.name").toLowerCase();
    
    if (os.indexOf("mac") >= 0) {
      return "mac";
    } else if (os.indexOf("win") >= 0) {
      return "win";
    } else {
      return "unsupported";
    }
  }

}
