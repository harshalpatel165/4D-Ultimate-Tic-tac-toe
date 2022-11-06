import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

import java.io.Serializable;

class TicTacToe extends GridPane implements Serializable {
    private javafx.event.EventHandler<ActionEvent> myHandler;
    public String piece = "";
    public GameButton[][] checkGrid = new GameButton[3][3];
}