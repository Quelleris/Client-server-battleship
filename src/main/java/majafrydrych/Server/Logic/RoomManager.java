package majafrydrych.Server.Logic;

import majafrydrych.Common.Logic.RoomData;

import java.util.ArrayList;
import java.util.Hashtable;


public class RoomManager {
    private static int roomIdCounter = 0;
    public static Hashtable<Integer, RoomData> rooms = new java.util.Hashtable<>();
    public static int getNewRoomId() {
        return ++roomIdCounter;
    }

    public static ArrayList<RoomData> getRoomList() {
        ArrayList<RoomData> roomList = new ArrayList<>();
        if (rooms != null && !rooms.isEmpty()) {
            for (RoomData room : rooms.values()) {
                roomList.add(room);
            }
        }
        return roomList;
    }

    public static RoomData createRoom(ClientHandler host) {
        int roomId = getNewRoomId();
        RoomData room = new RoomData(roomId, host.username);
        rooms.put(roomId, room);
        return room;
    }

    public static RoomData getRoom(int roomId) {
        return rooms.get(roomId);
    }
}
