/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Client.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Maja Frydrych
 */
public class Client implements Runnable {
    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;

    @Override
    public void run() {
        try {
            Socket client = new Socket("127.0.0.1", 20049);
            JOptionPane.showMessageDialog(null, "połączono z serwerem");
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "nie udało się połączyć z serwerem");
        }
    }
    
    
}
