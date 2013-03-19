package battleship.highscore;

import java.util.*;

/**
 * Highscore class
 * 
 * @date 31.12.2012
 * @author Christian Metz
 * @version 1.0
 */
class Highscore
{

  // select highscore data scorce
  private final String dataSource = "file";
  private final HighscoreAPI api;

  // An arraylist of the type "score" we will use to work with the scores inside the class
  private ArrayList<Score> scores = new ArrayList<Score>();

  // Maximal highscore size
  private final int HIGHSCORE_SIZE = 10;

  public Highscore()
  {
    if (dataSource.equals("file"))
    {
      api = new HighscoreFileAPI();
    } else {
      api = new HighscoreServerAPI();
    }
  }

  public void addScore(String name, int score)
  {
    scores = api.loadScore();
    scores.add(new Score(name, score, new Date()));
    api.updateScore(scores);
  }

  public int getScoreByName(String name)
  {
    ArrayList<Score> scores = getScores();
    // search name
    for (int i = 0; i < scores.size(); i++)
    {
      if (scores.get(i).getName().equals(name))
      {
        return (i + 1);
      }
    }
    // name not found
    return -1;
  }

  private void sort()
  {
    ScoreComparator comparator = new ScoreComparator();
    Collections.sort(scores, comparator);
  }

  private ArrayList<Score> getScores() {
    scores = api.loadScore();
    sort();
    return scores;
  }

  public String toString()
  {
    String output = "";
    
    ArrayList<Score> scores = getScores();
    int scoreSize = scores.size();
    
    // add table header
    output += "+=================== 👑  Highscore 👑  ====================+\n";
    output += "| Score | Points | Name                 | Date           |\n";
    output += "+-------+--------+----------------------+----------------+\n";
    
    // loop through all entries
    for (int i = 0; i < ((scoreSize > HIGHSCORE_SIZE) ? HIGHSCORE_SIZE : scoreSize); i++)
    {
      // access Score object by using: get(i) from the ArrayList
      output += String.format("| %-5d | %-6s | %-20s | %-14s |\n", (i + 1), scores.get(i).getPoints(), scores.get(i).getName(), scores.get(i).getTimestamp());
    }
    
    // add table footer
    output += "+========================================================+\n";
    return output;
  }
}