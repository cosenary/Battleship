package battleship.highscore;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class HighscoreServerAPI implements HighscoreAPI
{

  private final String HIGHSCORE_SERVER = "http://example.com";

  public ArrayList<Score> loadScore()
  {
    String data = makePostRequest("/load.php", "");
    return null;
  }

  public void updateScore(Score score)
  {
    String urlParams = urlParameters(score);
    makePostRequest("/update.php", urlParams);
  }

  private String makePostRequest(String endpoint, String params)
  {
    try
    {
      // Send the request
      URL url = new URL(HIGHSCORE_SERVER + endpoint);
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestProperty("charset", "utf-8");
      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
      
      // write parameters
      writer.write(params);
      writer.flush();
      
      // Get the response
      StringBuilder answer = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      
      while ((line = reader.readLine()) != null)
      {
        answer.append(line);
      }
      
      writer.close();
      reader.close();
      
      // return response
      return answer.toString();
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  private String urlParameters(Score score)
  {
    // Build parameter string
    return "name=" + score.getName() + "&points=" + score.getPoints() + "&hash=" + md5Hash(score.getName() + score.getPoints() + "82!i%2sdH5ecSD");
  }

  private String md5Hash(String data)
  {
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(data.getBytes());
      byte[] digest = md.digest();
      for (byte d : digest)
      {
        sb.append(Integer.toHexString((d & 0xFF) | 0x100).toLowerCase().substring(1, 3));
      }
    } catch (NoSuchAlgorithmException e) {
      System.out.println("MD5 generation failed: " + e);
    }
    return sb.toString();
  }

  @Override
  public void updateScore(ArrayList<Score> scores) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}