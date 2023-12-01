package majafrydrych.Common.Logic;

import java.io.Serializable;

public class RoomData implements Serializable {
    public int roomId;
    public String host;
    private String opponent;
    private boolean isGameStarted = false;
    public int playerNumber = 0;

    public RoomData(int roomId, String host) {
        this.roomId = roomId;
        this.host = host;
        playerNumber++;
    }

    public void addClient(String clientHandler) {
        if (playerNumber == 1) {
            opponent = clientHandler;
            playerNumber++;
        } else {
            System.out.println("Nie można dołączyć do pokoju, bo jest już pełny");
        }
    }

    public void removeClient(String clientHandler) {
        if (clientHandler == host) {
            host = null;
        } else if (clientHandler == opponent) {
            opponent = null;
        }
        playerNumber--;
    }
}
