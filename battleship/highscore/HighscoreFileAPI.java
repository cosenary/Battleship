package battleship.highscore;

import java.io.*;
import java.util.*;

/**
 * Highscore class
 * 
 * @date 28.12.2012
 * @author Christian Metz
 * @version 1.0
 */
public class HighscoreFileAPI implements HighscoreAPI
{

  // The name of the file where the highscores will be saved
  private final String HIGHSCORE_FILE = "battleship-score.txt";

  // Initialising an in and outputStream for working with the file
  private ObjectOutputStream outputStream = null;
  private ObjectInputStream inputStream = null;

  public ArrayList<Score> loadScore()
  {
    ArrayList<Score> scores = new ArrayList<Score>();
    try
    {
      inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
      scores = (ArrayList<Score>) inputStream.readObject();
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find Highscore txt file.");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } finally {
      try
      {
        if (outputStream != null)
        {
          outputStream.flush();
          outputStream.close();
        }
      } catch (IOException e) {
        System.out.println("IO Error: " + e.getMessage());
      }
    }
    return scores;
  }

  public void updateScore(ArrayList<Score> scores)
  {
    try
    {
      outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
      outputStream.writeObject(scores);
    } catch (FileNotFoundException e) {
      System.out.println("[Update] FNF Error: " + e.getMessage() + ", the program will try and make a new file");
    } catch (IOException e) {
      System.out.println("IO Error: " + e.getMessage());
    } finally {
      try {
        if (outputStream != null)
        {
          outputStream.flush();
          outputStream.close();
        }
      } catch (IOException e) {
        System.out.println("[Update] Error: " + e.getMessage());
      }
    }
  }

}