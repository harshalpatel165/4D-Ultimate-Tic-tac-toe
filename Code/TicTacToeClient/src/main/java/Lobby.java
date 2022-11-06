public class Lobby {
	Player host;
	boolean hostStatus;
	int numPlayers;
	Player playerList[];
	
	public Lobby() {
		host = null;
		hostStatus = false;
		numPlayers = 0;
	}
	
	public void setHost(Player host) {
		this.host = host;
		hostStatus = true;
		numPlayers++;
		playerList[numPlayers] = host;
	}
	
	public boolean isHost() {
		if(hostStatus) 
			return true;
		else
			return false;
	}
	
	public boolean isFull() {
		if (numPlayers >= 4)
			return true;
		else
			return false;
	}
	
	public void addPlayer(Player player) {
		numPlayers++;
		playerList[numPlayers] = player;
	}
	
	public static Player[] removeElement(Player arr[], int index){
		if (arr == null || index < 0 || index >= arr.length) {
	            return arr;
	        }
		Player[] arr2 = new Player[arr.length - 1];
		
		for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
             arr2[k++] = arr[i];
        }
		return arr2;
	}
	
	public void removePlayer(Player player) {
		for(int i = 0; i < numPlayers; i++) {
			if(playerList[i] == player) {
				removeElement(playerList,i);
				numPlayers--;
				break;
			}
		}
		
	}
}