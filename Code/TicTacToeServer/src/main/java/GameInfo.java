import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    boolean updateLobby;
    ArrayList<Player> playerList = new ArrayList<Player>();

    GameInfo(boolean updateLobby, ArrayList<Player> playerList){
        this.updateLobby = updateLobby;
        for(int i = 0; i < playerList.size(); i++){
            this.playerList.add(playerList.get(i));
        }
    }
}
