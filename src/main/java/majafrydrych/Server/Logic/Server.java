/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Server.Logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author Maja Frydrych
 */
public class Server implements Runnable {
    private ServerSocket server;
    private InetAddress ipAddress;
    private final int port;
    private ArrayList<ClientHandler> clients;
    private boolean serverClosed;
    private ExecutorService pool;
    
    public Server(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        clients = new ArrayList<ClientHandler>();
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
            JOptionPane.showMessageDialog(null, "Utworzono serwer :)");
            pool = Executors.newCachedThreadPool();
            while (!serverClosed) {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                clients.add(handler);
                pool.execute(handler);
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
           for (ClientHandler ch : clients) {
               ch.shutdown();
           }
       }
       catch (IOException e) {
           //ignore
       }
    }
}
