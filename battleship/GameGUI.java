package battleship;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Game's graphical user interface
 * 
 * @date 17.01.2012
 * @author Christian Metz
 * @version 2.0
 */
public class GameGUI extends JFrame implements GameUI
{

  // GameBoard properties (square dimension)
  private final int SIZE = 10;
  private final int FIELD_SIZE = 34; // = 32px because of the border

  // contains all JButtons of the grid
  private final JButton gameFieldGUI[][] = new JButton[10][10];
  private final JPanel gamePanel = new JPanel();

  // sound effect manager
  private final Effects effects = new Effects();
  private final GameField gameField;

  // resource path to the game's graphics
  private final String IMAGE_PATH = "../battleship/resources/images/";

  public GameGUI(GameField gameField)
  { 
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e);
    }
    
    // set GameField -> to use its shoot() method
    this.gameField = gameField;
    
    gamePanel.setLayout(new GridBagLayout());
    gamePanel.setPreferredSize(new Dimension(340, 340));
    gamePanel.setBackground(new Color(131, 209, 232));
    gamePanel.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
    
    buildFields();
    
    this.setTitle("Battleship");
    this.setPreferredSize(new Dimension(400, 400));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(gamePanel);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Display skrike shot
   * 
   * @param x
   * @param y 
   */
  public void displayStrike(int x, int y)
  {
    // get button and update its icon
    JButton field = getField(x, y);
    setFieldIcon(field, "strike.png");
  }

  /**
   * Display missed shot
   * 
   * @param x
   * @param y
   */
  public void displayMissed(int x, int y)
  {
    JButton field = getField(x, y);
    field.setBackground(new Color(85, 185, 218));
  }

  /**
   * Display sunken ship
   * 
   * @param ship
   */
  public void displayShip(Ship ship)
  {
    displayShip(ship.getType(), ship.getX(), ship.getY(), ship.isHorizontal(), ship.getLength());
  }

  /**
   * Display ship
   * - ship is sunken
   * - to see your own ships
   * 
   * @param type
   * @param x
   * @param y
   * @param horizontal
   * @param length 
   */
  public void displayShip(String type, int x, int y, boolean horizontal, int length)
  {
    // System.out.printf("type: %s, x: %d, y: %d, horizontal: %b length: %d\n", type, x, y, horizontal, length);
    
    // create new button
    JButton button = new JButton();
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
    // add click listener for sunken ships
    button.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(MouseEvent event)
      {
        System.out.println("Ship sunken");
      }
    });
    
    // grid prefs for the new button
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    
    // set position
    c.gridx = x;
    c.gridy = y;
    
    // button dimensions
    int width = 34;
    int height = 34;
    
    // occupy ship field in grid
    if (horizontal)
    {
      // horizontal
      c.gridwidth = length;
      c.gridheight = 1;
      width = 34 * length;
    } else {
      // vertical
      c.gridwidth = 1;
      c.gridheight = length;
      height = 34 * length;
    }
    
    // maybe update array reference ?!
    // setField(x, y, button);
    
    // select the ship type's preferences
    button.setPreferredSize(new Dimension(width, height));
    
    // remove old elements
    removeFields(x, y, horizontal, length);
    setFieldIcon(button, "ship_" + type + (horizontal ? "h" : "v") + ".png");
    
    // add new button and update the grid
    gamePanel.add(button, c);
    gamePanel.revalidate();
    gamePanel.repaint();
  }

  /**
   * Field action handler
   * 
   * @param x X coordinate
   * @param y y coordinate
   * @return strike
   */
  private boolean shoot(int x, int y)
  {
    // calls GameField's shoot method
    System.out.printf("At cell %d,%d\n", x, y);
    return gameField.shoot(x, y);
  }

  /**
   * Build fields with buttons and register action handler
   */
  private void buildFields()
  {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    
    for (int col = 0; col < 10; col++)
    {
      for (int row = 0; row < 10; row++)
      {
        JButton button = new JButton();
        button.setBackground(new Color(131, 209, 232));
        button.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new java.awt.Dimension(FIELD_SIZE, FIELD_SIZE));
        button.addMouseListener(new MouseAdapter()
        {
          @Override
          public void mousePressed(MouseEvent event)
          {
            JButton button = (JButton) event.getSource();
            Rectangle rectangle = button.getBounds();
            Point point = button.getLocation();
            
            // claculate field position
            int row = point.y / rectangle.height;
            int col = point.x / rectangle.width;
            
            // shoot on field
            shoot(col, row);
          }
        });
        
        // add button to GUI grid-manager
        setField(col, row, button);
        
        // set field position
        c.gridx = col;
        c.gridy = row;
        
        // add field to the grid
        gamePanel.add(button, c);
      }
    }
  }

  /**
   * Set object on specified field position
   * 
   * @param x
   * @param y
   */
  private void setField(int x, int y, JButton obj)
  {
    gameFieldGUI[y][x] = obj;
  }

  /**
   * Get object on specified field position
   * 
   * @param x
   * @param y
   * @return JButton instance on the field position
   */
  private JButton getField(int x, int y)
  {
    return gameFieldGUI[y][x];
  }

  /**
   * Remove fields
   * 
   * @see displayShip()
   * 
   * @param x
   * @param y
   * @param horizontal
   * @param length 
   */
  private void removeFields(int x, int y, boolean horizontal, int length)
  {
    for (int i = 0; i < length; i++)
    {
      if (horizontal)
      {
        gamePanel.remove(getField(x + i, y));
      } else {
        gamePanel.remove(getField(x, y + i));
      }
    }
  }

  /**
   * Helper to set Field icon
   * accoring to a image path
   * 
   * @see displayShip()
   * 
   * @param field
   * @param imgPath
   */
  private void setFieldIcon(JButton field, String imgPath)
  {
    try {
      Image img = ImageIO.read(getClass().getResource(IMAGE_PATH + imgPath));
      field.setIcon(new ImageIcon(img));
    } catch (IOException e) {
      System.out.println("Couldn't set field icon: " + e);
    }
  }

  public void addHighscore()
  {
    String s = (String) JOptionPane.showInputDialog(frame,
                    "Complete the sentence:\n"
                    + "\"Green eggs and...\"",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    icon,
                    possibilities,
                    "ham");

    //If a string was returned, say so.
    if ((s != null) && (s.length() > 0)) {
      setLabel("Green eggs and... " + s + "!");
      return;
    }
    
    //If you're here, the return value was null/empty.
    setLabel("Come on, finish the sentence!");
  }

}