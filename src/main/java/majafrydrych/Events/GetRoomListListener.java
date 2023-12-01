package majafrydrych.Events;

import majafrydrych.Common.Logic.RoomData;
import java.util.ArrayList;

public interface GetRoomListListener {
    void roomsListReceived(ArrayList<RoomData> roomsList);
}
