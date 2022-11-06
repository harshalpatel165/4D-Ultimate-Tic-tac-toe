import java.io.Serializable;

public class TurnIdentifier implements Serializable {
    private int turnID;

    TurnIdentifier(int TID){
        turnID = TID;
    }

    public int getTID(){
        return turnID;
    }
}

