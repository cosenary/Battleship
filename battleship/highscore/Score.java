package battleship.highscore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Score class
 * 
 * @date 3.01.2013
 * @author Christian Metz
 * @version 1.0
 */
class Score implements Serializable
{
  private final String name;
  private final int points;
  private Date timestamp;

  public Score(String name, int points, Date timestamp)
  {
    this.name = name;
    this.points = points;
    this.timestamp = timestamp;
  }

  public String getName()
  {
    return name;
  }

  public int getPoints()
  {
    return points;
  }

  public String getTimestamp()
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy");
    return dateFormat.format(timestamp);
  }
}