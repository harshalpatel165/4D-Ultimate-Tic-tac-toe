import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class ClientServerTest extends Application {
    private JFXPanel panel = new JFXPanel();


    DBConDao dbConDao = new DBConDao();
    chatMsg chat;
    chatMsg chat2;
    MasterBoard masterBoard;
    
    @Test
    void ClientServerConnection() throws IOException, InterruptedException {
        Client clientConnection;
        try {
            Server server = new Server(data -> {
                Platform.runLater(() -> {
                    if (data instanceof String) {

                    }
                });
            }, 5555);
        } catch (Exception e) {
            System.out.println("exception caught");
        }
        Thread.sleep(1000);
        try {
            clientConnection = new Client(data -> {
                Platform.runLater(() -> {
                    assertEquals("test went through", data.toString());
                });
            }, 5555);
            clientConnection.start();
            Thread.sleep(1000);
            clientConnection.send("test");
        } catch (Exception e) {
            System.out.println("exception caught2");
        }

    }

    @Test
    void ClientServerConnection2() throws IOException, InterruptedException {
        Client clientConnection;
        try {
            Server server = new Server(data -> {
                Platform.runLater(() -> {

                });
            }, 5555);
        } catch (Exception e) {
            System.out.println("exception caught");
        }
        Thread.sleep(1000);
        try {
            clientConnection = new Client(data -> {
                Platform.runLater(() -> {
                    Integer m = (Integer) data;
                    assertEquals(5, ((Integer) data).intValue());
                });
            }, 5555);
            clientConnection.start();
            Thread.sleep(1000);
            clientConnection.sendInt(5);
        } catch (Exception e) {
            System.out.println("exception caught2");
        }

    }


    @Test
    void ClientServerConnection3() throws IOException, InterruptedException {
        Client clientConnection;
        try {
            Server server = new Server(data -> {
                Platform.runLater(() -> {

                });
            }, 5555);
        } catch (Exception e) {
            System.out.println("exception caught");
        }
        Thread.sleep(500);
        try {
            clientConnection = new Client(data -> {
                Platform.runLater(() -> {
                    chatMsg m = (chatMsg) data;
                    assertEquals("0: Testing Chat", ((chatMsg) data).message);
                });
            }, 5555);
            clientConnection.start();
            Thread.sleep(500);
            clientConnection.sendChat("Testing Chat", clientConnection.TID);
        } catch (Exception e) {
            System.out.println("exception caught2");
        }

    }

    @Test
    void RegisterTest() throws SQLException, ClassNotFoundException {

        assertTrue(dbConDao.insertUser("Test1", "Test1"));
    }

    @Test
    void LoginTest() throws SQLException, ClassNotFoundException {
        DBConDao dbConDao = new DBConDao();
        assertTrue(dbConDao.login("Test1", "Test1"));
    }

    @Test
    void updateStatsTest() throws SQLException, ClassNotFoundException {
        DBConDao dbConDao = new DBConDao();
        assertTrue(dbConDao.updateStats(5,5,5));
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
    
    @Test
    void testTIDInitialization(){
        TurnIdentifier tid = new TurnIdentifier(0);
        assertEquals(0,tid.getTID(),0,"TID WAS NOT PROPERLY INITIALIZED");
    }


    @Test
    void testChatInitialization(){
        chat = new chatMsg("Test 1",1);
        assertEquals('1', chat.message.charAt(0), "TID IS INCORRECT");
        assertEquals("1: Test 1", chat.message, "MESSAGE IS INCORRECT");
    }

    @Test
    void testChatUpdate(){
        chat = new chatMsg("Test 1",1);
        chat2 = new chatMsg("Test 2", 2);
        chat = new chatMsg(chat2);

        assertEquals('2',chat.message.charAt(0),"TID DID NOT UPDATE CORRECTLY");
        assertEquals("2: Test 2", chat.message, "THE CHAT DID NOT UPDATE CORRECTLY");
    }

    @Test
    void testMasterBoard(){
        masterBoard = new MasterBoard();
        masterBoard.theBoard[0][0][0][0] = "0";
        masterBoard.theBoard[1][1][1][1] = "1";
        masterBoard.theBoard[2][2][2][2] = "2";
        assertEquals("MasterBoard",masterBoard.getClass().getName(), "Not a masterboard");

        MasterBoard masterboard2 = new MasterBoard(masterBoard);

        assertEquals("0",masterboard2.theBoard[0][0][0][0],"Board not correctly copied and updated");
        assertEquals("1",masterboard2.theBoard[1][1][1][1],"Board not correctly copied and updated");
        assertEquals("2",masterboard2.theBoard[2][2][2][2],"Board not correctly copied and updated");
    }
    
}
