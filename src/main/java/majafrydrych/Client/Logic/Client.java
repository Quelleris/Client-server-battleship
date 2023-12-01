/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package majafrydrych.Client.Logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import majafrydrych.Common.Logic.RoomData;
import majafrydrych.Common.Message.Message;
import majafrydrych.Events.GetRoomListListener;
import majafrydrych.Events.UsernameListener;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Maja Frydrych
 */
public class Client implements Runnable {
    private Socket client;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private final String username;
    private final int port;
    public boolean isConnected;
    private GetRoomListListener getRoomListListener;
    private UsernameListener usernameListener;
    
    public Client(String username, int port) {
        this.port = port;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", port);
            isConnected = true;
            writer = new ObjectOutputStream(client.getOutputStream());
            reader = new ObjectInputStream(client.getInputStream());
            sendMessage(new Message("username", username));
            listenForMessages();
        }
        catch (IOException e) {
            System.out.println("Błąd przy połączeniu z serwerem");
        }
    }

    public void setGetRoomListListener(GetRoomListListener getRoomListListener) {
        this.getRoomListListener = getRoomListListener;
    }

    public void setUsernameListener(UsernameListener usernameListener) {
        this.usernameListener = usernameListener;
    }
    
    public void sendMessage(Message messageToServer) {
        try {
            writer.writeObject(messageToServer);
            System.out.println("wiadomość wysłana: " + messageToServer.type);

        } catch(Exception e) {
            System.out.println("Błąd przy wysyłaniu wiadomości do serwera");
            closeEverything();
        }
    }
    
    public void listenForMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (client.isConnected()) {
                    try {
                        Message messageFromServer = (Message) reader.readObject();
                        handleServerMessage(messageFromServer);
                        System.out.println("wiadomość odebrana od serwera: " + messageFromServer.type);
                    }
                    catch(IOException | ClassNotFoundException ex) {
                        System.out.println("Błąd przy odbieraniu wiadomości od serwera");
                        closeEverything();
                    }
                }
            }
        }).start();
    }

    private void handleServerMessage(Message messageFromServer) {
        Gson gson = new Gson();
        switch (messageFromServer.type) {
            case "username":
                if (usernameListener != null) {
                    if (messageFromServer.stringMessage.equals("accepted")) {
                        usernameListener.usernameValidated(true);
                    }
                    else if (messageFromServer.stringMessage.equals("rejected")) {
                        usernameListener.usernameValidated(false);
                        closeEverything();
                    }
                }
                break;
            case "roomList":
                Type roomListType = new TypeToken<ArrayList<RoomData>>() {}.getType();
                ArrayList<RoomData> roomList = gson.fromJson(messageFromServer.stringMessage, roomListType);
                if (getRoomListListener != null) {
                    getRoomListListener.roomsListReceived(roomList);
                }
                break;
            case "createdRoom":
                RoomData room = gson.fromJson(messageFromServer.stringMessage,RoomData.class);
                System.out.println("Stworzono pokój " + room.roomId + " graczy: " + room.playerNumber);
                break;
            default:
                System.out.println("Nieznany typ wiadomości od serwera: " + messageFromServer.type);
                break;
        }
    }

    public void requestRoomList() {
        sendMessage(new Message("requestRoomList"));
    }

    public void requestCreateRoom() {
        sendMessage(new Message("createRoom"));
    }

    public void requestLeaveServer() {
        sendMessage(new Message("leaveServer"));
    }

    private void closeEverything() {
        isConnected = false;
        System.out.println("Zamykanie połączenia z serwerem");
        try {
//            if (reader != null) {
//                reader.close();
//            }
//            if (writer != null) {
//                writer.close();
//            }
            if (client != null) {
                client.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
