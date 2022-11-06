import java.io.Serializable;
public class BoardInfo implements Serializable{
    private TicTacToe[] boardArray = new TicTacToe[9];
    int outerRow;
    int outerCol;
    int row;
    int col;
    int outerArrayIndex;

    BoardInfo(TicTacToe[] boardArray, GameButton b){
        for(int i = 0; i < boardArray.length; i++){
            this.boardArray[i] =boardArray[i];
        }
        this.outerRow = b.outerRow;
        this.outerCol = b.outerCol;
        this.outerArrayIndex = b.outerArrayIndex;
        this.row = b.getRow();
        this.col = b.getCol();
    }

}