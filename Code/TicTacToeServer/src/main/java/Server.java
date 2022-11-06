import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.javatuples.Quartet;


public class Server {
     ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
     ArrayList<Player> playersList = new ArrayList<Player>();
     TheServer server;
     private Consumer<Serializable> callback;
     MasterBoard masterBoard = new MasterBoard();
    GameLogic logic = new GameLogic();
    int CID;
     int count = 0;

      Server(Consumer<Serializable> call, int portNum){
        callback = call;
        server = new TheServer(portNum);
        server.start();
      }

      public class TheServer extends Thread{
          int portNum;

          public TheServer(int port) {
              this.portNum = port;
          }
        public void run() {
            try(ServerSocket mysocket = new ServerSocket(portNum);){
                callback.accept("Server is waiting for the client");
                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), ++count);
                    callback.accept("Player " + (count) + " has joined the server");
                    clients.add(c);
                    c.start();
                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
      }

      public class ClientThread extends Thread {
          Socket connection;
          TurnIdentifier clientNumber;

          ObjectInputStream in;
          ObjectOutputStream out;
          MasterBoard clientBoard = new MasterBoard();

          ClientThread(Socket s, int count) {
              this.connection = s;
              this.clientNumber = new TurnIdentifier(count);
              CID = 1;
          }

          public void updateClients(GameInfo gameInfo) {
              for(int i = 0; i < clients.size(); i++) {
                  ClientThread t = clients.get(i);
                  try {
                      t.out.writeObject(t.clientNumber);
                      t.sleep(100);
                      t.out.writeObject(gameInfo);
                  }
                  catch(Exception e) {}
              }
          }

          public void sendAllString(String data) {
              for(int i=0; i<clients.size();i++) {
                  ClientThread t = clients.get(i);
                  try {
                      t.out.writeObject(data);
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }

          public void sendAllBoard(MasterBoard sendBoard){
              for(int i=0; i<clients.size(); i++){
                  ClientThread t = clients.get(i);
                  try{
                      t.out.writeObject(sendBoard);
                  }catch (IOException e){
                      e.printStackTrace();
                  }
              }
          }


          public void sendAllChat(chatMsg m){
              System.out.println("IN THE CHAT FUNCTION");
              for(int i=0; i<clients.size(); i++){
                  ClientThread t = clients.get(i);
                  try{
                      t.out.writeObject(m);
                  }catch (IOException e){
                      e.printStackTrace();
                  }
              }
          }




          public void setWinner(BoardInfo board, int player){
              if(board.outerRow == 0 && board.outerCol == 0){
                  masterBoard.isWinner[0] = true;
                  masterBoard.whoWon[0] = player;
              }
              else if(board.outerRow == 0 && board.outerCol == 1){
                  masterBoard.isWinner[1] = true;
                  masterBoard.whoWon[1] = player;
              }
              else if(board.outerRow == 0 && board.outerCol == 2){
                  masterBoard.isWinner[2] = true;
                  masterBoard.whoWon[2] = player;
              }
              else if(board.outerRow == 1 && board.outerCol == 0){
                  masterBoard.isWinner[3] = true;
                  masterBoard.whoWon[3] = player;
              }
              else if(board.outerRow == 1 && board.outerCol == 1){
                  masterBoard.isWinner[4] = true;
                  masterBoard.whoWon[4] = player;
              }
              else if(board.outerRow == 1 && board.outerCol == 2){
                  masterBoard.isWinner[5] = true;
                  masterBoard.whoWon[5] = player;
              }
              else if(board.outerRow == 2 && board.outerCol == 0){
                  masterBoard.isWinner[6] = true;
                  masterBoard.whoWon[6] = player;
              }
              else if(board.outerRow == 2 && board.outerCol == 1){
                  masterBoard.isWinner[7] = true;
                  masterBoard.whoWon[7] = player;
              }
              else if(board.outerRow == 2 && board.outerCol == 2){
                  masterBoard.isWinner[8] = true;
                  masterBoard.whoWon[8] = player;
              }
          }

          public void checkWinner2(BoardInfo board) throws IOException {
              boolean isPoint = logic.isPointScored2(masterBoard,board,clientNumber.getTID());
              if(isPoint == true){
                  setWinner(board,clientNumber.getTID());
                  out.writeObject("point" + clientNumber.getTID());
                  if(logic.isWinner2(masterBoard) == true){
                      // Game is won by clientNumber.getTID()
                      System.out.println("WINNER!");
                      System.out.print(clientNumber.getTID());
                      System.out.println("WON THE GAME");
                      masterBoard.endGame = true;
                      out.writeObject("win" + clientNumber.getTID());
                  }
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


          public void run(){
            try {
                out = new ObjectOutputStream(connection.getOutputStream());
                in = new ObjectInputStream(connection.getInputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }
            while(true) {
                try {
                    while(playersList.size() > 0) {
                        Player currentPlayer = playersList.get(CID - 1);
                        System.out.print("CID in client thread #"+ clientNumber.getTID() +": " + CID);
                        if (currentPlayer.isAI) {
                            Robot bot;
                            if (currentPlayer.getDifficulty() == "Easy") {
                                bot = new EasyRobot(CID, masterBoard);

                            } else if (currentPlayer.getDifficulty() == "Medium") {
                                bot = new MediumRobot(CID, masterBoard);
                            }
                            else {
                                bot = new MediumRobot(CID, masterBoard);

                            }
                            Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();
                            System.out.println("R: " + location.getValue(0) + "C: " + location.getValue(1) + "r: " + location.getValue(2) + "c: " + location.getValue(3));
                            masterBoard.theBoard[(int)location.getValue(0)][(int)location.getValue(1)][(int)location.getValue(2)][(int)location.getValue(3)] = String.valueOf(bot.getMarker());
                            masterBoard.printAll();
                            //checkWinner2(board);
                            updateCID();
                            MasterBoard newBoard = new MasterBoard(masterBoard);
                            try{
                                wait(10);
                            }
                            catch(Exception e){}
                            sendAllBoard(newBoard);
                            //sendAllString("Valid Move");
                        }
                        else break;
                    }
                    Object data = in.readObject();

                    if (data instanceof String ) {
                        if(data.toString().equals("test")){
                            out.writeObject("Test Passed");
                            callback.accept("Test Passed Through ServerGUI");
                            continue;
                        }
                        if (data.toString().equals("new Easy AI")) {

                            System.out.println("Easy AI was added");
                            count = count + 1;
                            Player player = new Player(count);
                            player.isAI = true;
                            player.setDifficulty("Easy");
                            playersList.add(player);
                            GameInfo gameInfo = new GameInfo(true, playersList);
                            System.out.println("Size" + gameInfo.playerList.size());
                            System.out.println("Size" + playersList.size());
                            updateClients(gameInfo);
                        }
                        if (data.toString().equals("new Medium AI")) {

                            System.out.println("Medium AI was added");
                            count = count + 1;
                            Player player = new Player(count);
                            player.isAI = true;
                            player.setDifficulty("Medium");
                            playersList.add(player);
                            GameInfo gameInfo = new GameInfo(true, playersList);
                            System.out.println("Size" + gameInfo.playerList.size());
                            System.out.println("Size" + playersList.size());
                            updateClients(gameInfo);
                        }

                        if (data.toString().equals("new Player")) {
                            System.out.println("Player was added");
                            Player player = new Player(count);
                            playersList.add(player);
                            GameInfo gameInfo = new GameInfo(true, playersList);
                            System.out.println("Size" + gameInfo.playerList.size());
                            updateClients(gameInfo);
                        }
                    }

                    if(data instanceof Integer){
                        out.writeObject(5);
                    }
                    if(data instanceof chatMsg){
                        chatMsg msg = (chatMsg) data;
                        System.out.println(msg.message);
                        chatMsg outMsg = new chatMsg(msg);
                        try{
                            wait(100);
                        }
                        catch(Exception e){}
                        System.out.println("sending it to all");
                        sendAllChat(outMsg);
                    }



                    if(data instanceof BoardInfo){
                        BoardInfo board = (BoardInfo) data;
                        System.out.println("row: " + board.row);
                        System.out.println("col: " + board.col);
                        System.out.println("outer row: " + board.outerRow);
                        System.out.println("outer col: " + board.outerCol);
                        masterBoard.theBoard[board.outerRow][board.outerCol][board.row][board.col] = String.valueOf(clientNumber.getTID());
                        masterBoard.printAll();
                        checkWinner2(board);
                        updateCID();
                        MasterBoard newBoard = new MasterBoard(masterBoard);
                        try{
                            wait(10);
                        }
                        catch(Exception e){}
                        sendAllBoard(newBoard);
                        //sendAllString("Valid Move");
                    }

                }
                catch(Exception e) {
                    callback.accept("Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }
      }



}
