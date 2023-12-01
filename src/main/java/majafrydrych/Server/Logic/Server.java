package majafrydrych.Server.Logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Maja Frydrych
 */
public class Server implements Runnable {
    private ServerSocket server;
    private InetAddress ipAddress;
    private final int port;
    private boolean serverClosed;
    
    public Server(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        serverClosed = false;
    }
    
    public InetAddress getIpAddress() {
        return this.ipAddress;
    }
    
    public int getPort() {
        return this.port;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.println("serwer działa");
            while (!serverClosed) {
                Socket client = server.accept();
                System.out.println("Dołączył nowy klient");
                ClientHandler handler = new ClientHandler(client);
                Thread thread = new Thread(handler);
                thread.start();
            }
            
        } catch (IOException ex) {
            shutdown();
        }
    }
    
    public void shutdown() {
       try {
           serverClosed = true;
           if (!server.isClosed()) {
               server.close();
           }
       }
       catch (IOException e) {
           //ignore
       }
    }
}
