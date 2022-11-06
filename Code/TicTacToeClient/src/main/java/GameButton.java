import javafx.scene.control.Button;

import java.io.Serializable;

class GameButton extends Button implements Serializable {
    private int row;
    private int col;
    private int playerMove;
    int outerArrayIndex;
    int outerRow;
    int outerCol;
    String Piece;
    GameButton(int row, int col){
        this.row = row;
        this.col = col;
        playerMove = 0;
        Piece = "";
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    int getPlayerMove() {
        return playerMove;
    }

    void setPlayerMove(int playerMove) {
        this.playerMove = playerMove;
    }
}