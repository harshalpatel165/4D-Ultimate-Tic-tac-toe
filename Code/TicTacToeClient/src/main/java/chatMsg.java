import java.io.Serializable;

public class chatMsg implements Serializable {
    String message;
    chatMsg(String S, int tid){
        message = String.valueOf(tid) + ": " + S;
    }
    chatMsg(chatMsg m){
        message = m.message;
    }
}
