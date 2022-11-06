import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;
import org.javatuples.Quartet;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
public class AITest extends Application {
    private JFXPanel panel = new JFXPanel();
    int marker = 2;
    MasterBoard master;

    @Test
    void MediumAIHoirzontalTest1() {
        master = new MasterBoard();
        master.whoWon[0] = 1;
        master.whoWon[1] = 1;
        master.theBoard[0][0][0][0] = "1";
        master.theBoard[0][0][0][1] = "1";
        master.theBoard[0][0][0][2] = "1";
        master.theBoard[0][1][0][0] = "1";
        master.theBoard[0][1][0][1] = "1";
        master.theBoard[0][1][0][2] = "1";
        master.theBoard[0][2][0][0] = "1";
        master.theBoard[0][2][0][1] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 0);
        assertEquals(location.getValue(1), 2);
        assertEquals(location.getValue(2), 0);
        assertEquals(location.getValue(3), 2);

    }

    @Test
    void MediumAIHorizontalTest2() {
        master = new MasterBoard();
        master.whoWon[1] = 1;
        master.whoWon[2] = 1;
        master.theBoard[0][0][0][1] = "1";
        master.theBoard[0][0][0][2] = "1";
        master.theBoard[0][1][0][0] = "1";
        master.theBoard[0][1][0][1] = "1";
        master.theBoard[0][1][0][2] = "1";
        master.theBoard[0][2][0][0] = "1";
        master.theBoard[0][2][0][1] = "1";
        master.theBoard[0][2][0][2] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 0);
        assertEquals(location.getValue(1), 0);
        assertEquals(location.getValue(2), 0);
        assertEquals(location.getValue(3), 0);
    }

    @Test
    void MediumAIVerticalTest1() {
        master = new MasterBoard();
        master.whoWon[0] = 1;
        master.whoWon[3] = 1;
        master.theBoard[0][0][0][0] = "1";
        master.theBoard[0][0][1][0] = "1";
        master.theBoard[0][0][2][0] = "1";
        master.theBoard[1][0][0][0] = "1";
        master.theBoard[1][0][1][0] = "1";
        master.theBoard[1][0][2][0] = "1";
        master.theBoard[2][0][0][0] = "1";
        master.theBoard[2][0][1][0] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 2);
        assertEquals(location.getValue(1), 0);
        assertEquals(location.getValue(2), 2);
        assertEquals(location.getValue(3), 0);
    }

    @Test
    void MediumAIVerticalTest2() {
        master = new MasterBoard();
        master.whoWon[3] = 1;
        master.whoWon[6] = 1;
        master.theBoard[0][0][1][0] = "1";
        master.theBoard[0][0][2][0] = "1";
        master.theBoard[1][0][0][0] = "1";
        master.theBoard[1][0][1][0] = "1";
        master.theBoard[1][0][2][0] = "1";
        master.theBoard[2][0][0][0] = "1";
        master.theBoard[2][0][1][0] = "1";
        master.theBoard[2][0][2][0] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 0);
        assertEquals(location.getValue(1), 0);
        assertEquals(location.getValue(2), 0);
        assertEquals(location.getValue(3), 0);
    }

    @Test
    void MediumAIDiagonalTest1() {
        master = new MasterBoard();
        master.whoWon[0] = 1;
        master.whoWon[4] = 1;
        master.theBoard[0][0][0][0] = "1";
        master.theBoard[0][0][1][1] = "1";
        master.theBoard[0][0][2][2] = "1";
        master.theBoard[1][1][0][0] = "1";
        master.theBoard[1][1][1][1] = "1";
        master.theBoard[1][1][2][2] = "1";
        master.theBoard[2][2][0][0] = "1";
        master.theBoard[2][2][1][1] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 2);
        assertEquals(location.getValue(1), 2);
        assertEquals(location.getValue(2), 2);
        assertEquals(location.getValue(3), 2);
    }

    @Test
    void MediumAIDiagonalTest2() {
        master = new MasterBoard();
        master.whoWon[4] = 1;
        master.whoWon[8] = 1;

        master.theBoard[0][0][1][1] = "1";
        master.theBoard[0][0][2][2] = "1";
        master.theBoard[1][1][0][0] = "1";
        master.theBoard[1][1][1][1] = "1";
        master.theBoard[1][1][2][2] = "1";
        master.theBoard[2][2][0][0] = "1";
        master.theBoard[2][2][1][1] = "1";
        master.theBoard[2][2][1][1] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 0);
        assertEquals(location.getValue(1), 0);
        assertEquals(location.getValue(2), 0);
        assertEquals(location.getValue(3), 0);
    }

    @Test
    void MediumAIDiagonalTest3() {
        master = new MasterBoard();
        master.whoWon[2] = 1;
        master.whoWon[4] = 1;

        master.theBoard[0][2][0][2] = "1";
        master.theBoard[0][2][1][1] = "1";
        master.theBoard[0][2][2][0] = "1";
        master.theBoard[1][1][0][2] = "1";
        master.theBoard[1][1][1][1] = "1";
        master.theBoard[1][1][2][0] = "1";
        master.theBoard[2][0][0][2] = "1";
        master.theBoard[2][0][1][1] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 2);
        assertEquals(location.getValue(1), 0);
        assertEquals(location.getValue(2), 2);
        assertEquals(location.getValue(3), 0);
    }

    @Test
    void MediumAIDiagonalTest4() {
        master = new MasterBoard();
        master.whoWon[4] = 1;
        master.whoWon[6] = 1;

        master.theBoard[0][2][1][1] = "1";
        master.theBoard[0][2][2][0] = "1";
        master.theBoard[1][1][0][2] = "1";
        master.theBoard[1][1][1][1] = "1";
        master.theBoard[1][1][2][0] = "1";
        master.theBoard[2][0][0][2] = "1";
        master.theBoard[2][0][1][1] = "1";
        master.theBoard[2][0][2][0] = "1";

        MediumRobot bot = new MediumRobot(marker, master);
        Quartet<Integer, Integer, Integer, Integer> location = bot.makeMove();

        assertEquals(location.getValue(0), 0);
        assertEquals(location.getValue(1), 2);
        assertEquals(location.getValue(2), 0);
        assertEquals(location.getValue(3), 2);
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}