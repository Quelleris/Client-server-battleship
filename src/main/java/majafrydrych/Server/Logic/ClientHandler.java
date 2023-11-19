/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Server.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author sugil
 */
 public class ClientHandler implements Runnable {
        private final Socket client;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket client) {
            this.client= client;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream(), true);

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    writer.println("Server echoes: " + message);
                }
            } catch (IOException e) {
                System.out.println("Error occurred while handling client connection: " + e.getMessage());
            }
        }
        
        public void shutdown() {
            try {
                reader.close();
                writer.close();
                if (!client.isClosed()) {
                    client.close();
                }
            }
            catch (IOException e) {
                //ignore
            }
        }
    }
