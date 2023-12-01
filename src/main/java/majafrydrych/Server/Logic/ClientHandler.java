/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Server.Logic;

import com.google.gson.Gson;
import majafrydrych.Common.Logic.RoomData;
import majafrydrych.Common.Message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Maja Frydrych
 */
 public class ClientHandler implements Runnable {
        public static ArrayList<ClientHandler> allClientHandlers = new ArrayList<>();
        private Socket client;
        private ObjectOutputStream writer;
        private ObjectInputStream reader;
        public String username;
        private boolean isConnected;

        public ClientHandler(Socket client) {
            try {
                this.client = client;
                writer = new ObjectOutputStream(client.getOutputStream());
                reader = new ObjectInputStream(client.getInputStream());
            }
            catch (IOException e) {
                System.out.println("Błąd przy tworzeniu strumieni wejścia/wyjścia");
                closeEverything();
            }
        }

        @Override
        public void run() {
            isConnected = true;
            Message messageFromClient;

            while (isConnected) {
                try {
                    messageFromClient = (Message) reader.readObject();
                    if (messageFromClient == null) {
                        System.out.println("Klient rozłączony");
                        closeEverything();
                        break;
                    }
                    System.out.println("wiadomość odebrana: " + messageFromClient.type);
                    handleMessageFromClient(messageFromClient);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Błąd przy odbieraniu wiadomości od klienta");
                    closeEverything();
                    break;
                }
            }
        }

        private void handleMessageFromClient(Message messageFromClient) throws IOException {
            Gson gson = new Gson();
            String json;
            switch (messageFromClient.type) {
                case "username":
                    if (isUsernameUnique(messageFromClient.stringMessage)) {
                        username = messageFromClient.stringMessage;
                        allClientHandlers.add(this);
                        sendMessageToClient(new Message("username", "accepted"));
                    } else {
                        sendMessageToClient(new Message("username", "rejected"));
                        closeEverything();
                    }
                    break;
                case "requestRoomList":
                    json = gson.toJson(RoomManager.getRoomList());
                    sendMessageToClient(new Message("roomList", json));
                    break;
                case "createRoom":
                    RoomData room = RoomManager.createRoom(this);
                    json = gson.toJson(room);
                    sendMessageToClient(new Message("createdRoom", json));
                    break;
                default:
                    System.out.println("Nieznany typ wiadomości od klienta: " + messageFromClient.type);
                    break;
            }
        }

    private boolean isUsernameUnique(String stringMessage) {
            if (allClientHandlers != null && allClientHandlers.isEmpty()) {
                return true;
            }
            for (ClientHandler clientHandler : allClientHandlers) {
                if (clientHandler.username.equals(stringMessage)) {
                    return false;
                }
            }
            return true;
    }

    private void closeEverything() {
            isConnected = false;
            allClientHandlers.remove(this);
            System.out.println("Klient rozłączony");
            try {
                if (client != null && !client.isClosed()) {
                    client.close();
                }
//                if (reader != null) {
//                    reader.close();
//                }
//                if (writer != null) {
//                    writer.close();
//                }
            } catch (IOException e) {
                System.out.println("Błąd przy zamykaniu połączenia z klientem");
            }
    }

    private void sendMessageToClient(Message messageToClient) {
            try {
                writer.writeObject(messageToClient);
                writer.flush();
                System.out.println("wiadomość wysłana: " + messageToClient.type);
            } catch (Exception e) {
                System.out.println("Błąd przy wysyłaniu wiadomości do klienta");
                closeEverything();
            }
    }
 }
