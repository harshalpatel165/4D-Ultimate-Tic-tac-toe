import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import java.util.ArrayList;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GameLogicTest extends Application {

    private JFXPanel panel = new JFXPanel();
    private GameButton b1 = new GameButton(0,0);;
    static GameLogic logic = new GameLogic();;
    static TicTacToe[] boardArray = new TicTacToe[9];;
    BoardInfo board1;
    //GameButton b1;

//    @BeforeAll
//    static void setup() {
//        logic
//        b1
//        boardArray
//    }


    @Test
    void IndividualGridTest1() {
        MasterBoard board = new MasterBoard();
        board.theBoard[0][0][0][0] = "1";
        board.theBoard[0][0][1][0] = "1";
        board.theBoard[0][0][2][0] = "1";
        b1.outerRow = 0;
        b1.outerCol = 0;
        BoardInfo temp = new BoardInfo(boardArray,b1);

        assertEquals(logic.isPointScored2(board, temp,1),true,"Incorrect IndividualGridTest1");
    }

    @Test
    void IndividualGridTest2() {
        MasterBoard board = new MasterBoard();
        board.theBoard[0][0][0][0] = "1";
        board.theBoard[0][0][1][0] = "1";
        board.theBoard[0][0][2][0] = "1";
        board.theBoard[1][1][0][0] = "2";
        board.theBoard[1][1][1][1] = "2";
        board.theBoard[1][1][2][2] = "2";

        b1.outerRow = 1;
        b1.outerCol = 1;
        BoardInfo temp = new BoardInfo(boardArray,b1);

        assertEquals(logic.isPointScored2( board, temp,2),true,"Incorrect IndividualGridTest2");
    }

    @Test
    void IndividualGridTest3() {
        MasterBoard board = new MasterBoard();
        board.theBoard[0][0][0][0] = "1";
        board.theBoard[0][0][1][0] = "1";
        board.theBoard[0][0][2][0] = "1";
        board.theBoard[1][1][0][0] = "2";
        board.theBoard[1][1][1][1] = "2";
        board.theBoard[1][1][2][2] = "2";
        board.theBoard[2][2][0][0] = "3";
        board.theBoard[2][2][1][0] = "3";
        board.theBoard[2][2][2][0] = "3";

        b1.outerRow = 2;
        b1.outerCol = 2;
        BoardInfo temp = new BoardInfo(boardArray,b1);

        assertEquals(logic.isPointScored2( board, temp,3),true,"Incorrect IndividualGridTest3");
    }

    @Test
    void IndividualGridTest4() {
        MasterBoard board = new MasterBoard();
        board.theBoard[0][0][0][0] = "1";
        board.theBoard[0][0][1][0] = "1";
        board.theBoard[0][0][2][0] = "1";
        board.theBoard[1][1][0][0] = "2";
        board.theBoard[1][1][1][1] = "2";
        board.theBoard[1][1][2][2] = "2";
        board.theBoard[2][2][0][0] = "3";
        board.theBoard[2][2][1][0] = "3";
        board.theBoard[2][2][2][0] = "3";
        board.theBoard[2][2][0][2] = "4";
        board.theBoard[2][2][1][2] = "4";
        board.theBoard[2][2][2][2] = "4";

        b1.outerRow = 2;
        b1.outerCol = 2;
        BoardInfo temp = new BoardInfo(boardArray,b1);

        assertEquals(logic.isPointScored2( board, temp,4),true,"Incorrect IndividualGridTest4");
    }

    @Test
    void IndividualGridTest5() {
        MasterBoard board = new MasterBoard();

        board.theBoard[2][2][0][1] = "4";
        board.theBoard[2][2][1][1] = "4";
        board.theBoard[2][2][2][1] = "4";

        b1.outerRow = 2;
        b1.outerCol = 2;
        BoardInfo temp = new BoardInfo(boardArray,b1);

        assertEquals(logic.isPointScored2( board, temp,4),true,"Incorrect IndividualGridTest5");
    }

    @Test
    void WinnerTest1() {
        MasterBoard board = new MasterBoard();
        board.isWinner[0] = true;
        board.isWinner[1] = true;
        board.isWinner[2] = true;
        assertEquals(logic.isWinner2( board),true,"Incorrect WinnerTest1");
    }
    @Test
    void WinnerTest2() {
        MasterBoard board = new MasterBoard();
        board.isWinner[3] = true;
        board.isWinner[4] = true;
        board.isWinner[5] = true;
        assertEquals(logic.isWinner2( board),true,"Incorrect WinnerTest2");
    }
    @Test
    void WinnerTest3() {
        MasterBoard board = new MasterBoard();
        board.isWinner[6] = true;
        board.isWinner[7] = true;
        board.isWinner[8] = true;
        assertEquals(logic.isWinner2( board),true,"Incorrect WinnerTest3");
    }
    @Test
    void WinnerTest4() {
        MasterBoard board = new MasterBoard();
        board.isWinner[0] = true;
        board.isWinner[4] = true;
        board.isWinner[8] = true;
        assertEquals(logic.isWinner2( board),true,"Incorrect WinnerTest4");
    }
    @Test
    void WinnerTest5() {
        MasterBoard board = new MasterBoard();
        board.isWinner[2] = true;
        board.isWinner[4] = true;
        board.isWinner[6] = true;
        assertEquals(logic.isWinner2( board),true,"Incorrect WinnerTest5");
    }
    @Test
    void WinnerTest6() {
        MasterBoard board = new MasterBoard();
        board.isWinner[2] = true;
        board.isWinner[4] = true;
        board.isWinner[8] = true;
        assertEquals(logic.isWinner2( board),false,"Incorrect WinnerTest6");
    }
    @Test
    void WinnerTest7() {
        MasterBoard board = new MasterBoard();
        board.isWinner[0] = true;
        board.isWinner[3] = true;
        board.isWinner[5] = true;
        assertEquals(logic.isWinner2( board),false,"Incorrect WinnerTest7");
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}