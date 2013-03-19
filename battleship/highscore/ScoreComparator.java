package battleship.highscore;

import java.util.Comparator;

/**
 * ScoreComparator class
 * 
 * @date 3.01.2013
 * @author Christian Metz
 * @version 1.0
 */
class ScoreComparator implements Comparator<Score>
{
  public int compare(Score score1, Score score2)
  {
    // get points
    int sc1 = score1.getPoints();
    int sc2 = score2.getPoints();
    
    // is score 1 higher than score 2
    if (sc1 > sc2)
    {
      return -1;
    } else if (sc1 < sc2) {
      return 1;
    } else {
      return 0;
    }
  }
}