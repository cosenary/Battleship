package battleship.network;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Christian Metz christian@metzweb.net
 */
public class Connection
{
  private final int PORT = 54321;

  public Connection()
  {
    Connector connector = new Connector(PORT); 
    connector.start(); 

    Socket socket;
    try {
      socket = new Socket("127.0.0.1", PORT) ;
      br = new BufferedReader(new InputStreamReader(sock.getInputStream())); 
      bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())); 
    } catch(Exception e) {
      System.out.println(e);
    }

    connection = new Thread() {
      public void run()
      {
        // receive input
        while (true)
        {
          try {
            String s = br.readLine(); 
            computer_move(s); 
          } catch(Exception e) {
            System.out.println(e);
          }
        }  
      }
    };
    
    connection.start() ;
  }
}
