import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;
    int port;
    int TID;
    int CID = 1;
    MasterBoard myBoard = new MasterBoard();


    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call, int port) throws IOException {
        this.port = port;
        callback = call;
        socketClient= new Socket("127.0.0.1",port);
    }

    public void run(){

        try {
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {
            System.out.print("does it reach error");
        }

        while(true) {

            try {
                Object data = in.readObject();

                if(data instanceof TurnIdentifier){
                    TID = ((TurnIdentifier) data).getTID();
                    System.out.println("I have turn: " + TID);
                }

                if(data instanceof GameInfo) {
                    GameInfo gi = (GameInfo) data;
                    System.out.println("inside client: " + gi.playerList.get(0).isAI);
                    System.out.println("inside client: " + gi.updateLobby);
                    System.out.println("inside client: " + gi.playerList.size());

                    //System.out.println("player extra card after recieving: " + data.playerExtraDrawCard.value + data.playerExtraDrawCard.suite);
                    //System.out.println("banker extra card after recieving: " + data.bankerExtraDrawCard.value + data.bankerExtraDrawCard.suite);
                    callback.accept(gi);
                }

                if(data instanceof String){
                    if(data.toString().equals("Test Passed")){
                        callback.accept("test went through");
                    }
                    if(data.toString().equals("Valid Move")){
                        updateCID();
                        System.out.println("The current CID IS : " +CID);
                    }
                    if(data.toString().contains("point")){
                        String str = data.toString().substring(5);
                        if(Integer.parseInt(str) == TID){
                            callback.accept("point");
                            System.out.print("point was given to this client----------------------------------------");
                        }
                    }
                    if(data.toString().contains("win")){
                        String str = data.toString().substring(3);
                        if(Integer.parseInt(str) == TID){
                            callback.accept("win");
                        }
                    }

                }

                if(data instanceof MasterBoard){
                    updateCID();
                    myBoard = new MasterBoard((MasterBoard) data);
                    myBoard.printAll();
                    callback.accept(myBoard);
                }

                if(data instanceof chatMsg){
                    chatMsg m = (chatMsg) data;
                    callback.accept(m);
                }

            }
            catch(Exception e) {}
        }

    }

    public void send(String data) {

        try {
            out.writeObject(data);
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void acceptBoard (MasterBoard data){
        myBoard = new MasterBoard(data);
        myBoard.printAll();
    }

    public void sendBoard(BoardInfo data){
        try{
            out.writeObject(data);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendChat(String s, int TID){
        chatMsg msg = new chatMsg(s, TID);
        try{
            out.writeObject(msg);
            System.out.println(msg.message);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendInt(int data){
        try{
            out.writeObject(data);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void updateCID(){
        if(CID>=1 && CID <=3){
            CID++;
        }
        else{
            CID =1;
        }
    }


}
