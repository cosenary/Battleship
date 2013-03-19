package battleship.highscore;

import java.util.ArrayList;

interface HighscoreAPI
{
  public ArrayList<Score> loadScore();
  public void updateScore(ArrayList<Score> scores);
}