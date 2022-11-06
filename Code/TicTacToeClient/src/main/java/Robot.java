import java.util.*;
import org.javatuples.Quartet;
abstract class Robot {
    public int getMarker() { return marker; }
    public void setMarker(int marker) { this.marker = marker; }

    int marker = 0;
    MasterBoard boards;

    public abstract Quartet<Integer, Integer, Integer, Integer> makeMove();
}
class EasyRobot extends Robot {
    EasyRobot(int marker, MasterBoard boards) {
        this.marker = marker;
        this.boards = boards;
    }
    public Quartet<Integer, Integer, Integer, Integer> makeMove() {
        int outerCol = (int)(Math.random() * 3);
        int outerRow = (int)(Math.random() * 3);
        int innerCol = (int)(Math.random() * 3);
        int innerRow = (int)(Math.random() * 3);
        return new Quartet<Integer, Integer, Integer, Integer>(outerRow, outerCol, innerRow, innerCol);
    }
}
class MediumRobot extends Robot {
    MediumRobot(int marker, MasterBoard boards) {
        this.marker = marker;
        this.boards = boards;
    }
    public Quartet<Integer, Integer, Integer, Integer> makeMove() {
        int outerCol = 0, outerRow = 0, innerCol = 0, innerRow = 0;
        boolean threatFound = false;
        int piece = -1;
        MasterBoard board = new MasterBoard();
        for (int h = 0; h < 3; h++) {
            for (int i = 0; i < 3; i++) {
                piece = boards.whoWon[(h * 3) + i];
                if (piece == 0) continue;
                for (int j = -1; j < 2; j++)
                    for (int k = -1; k < 2; k++) {
                        if ((h + j >= 0 && i + k >= 0 && h + j < 3 && i + k < 3 && boards.whoWon[(h + j) + ((i + k) * 3)] == piece)) {

                            if (j == 0 && k == 0) continue;
                            int firstPieceCol = h;
                            int firstPieceRow = i;
                            int secondPieceCol = firstPieceCol + j;
                            int secondPieceRow = firstPieceRow + k;
                            int horiDir = -1 * (firstPieceCol - secondPieceCol);
                            int vertDir = -1 * (firstPieceRow - secondPieceRow);
                            outerCol = 0;
                            outerRow = 0;
                            if (secondPieceCol + horiDir > 2 || secondPieceCol + horiDir < 0)
                                outerCol = firstPieceCol - horiDir;
                            else
                                outerCol = secondPieceCol + horiDir;
                            if (secondPieceRow + vertDir > 2 || secondPieceRow + vertDir < 0)
                                outerRow = firstPieceRow - vertDir;
                            else
                                outerRow = secondPieceRow + vertDir;
                            k = 2;
                            j = 2;
                            i = 3;
                            h = 3;
                            threatFound = true;
                            break;
                        }
                    }
            }
        }
        String[][] grid = board.theBoard[outerRow][outerCol];
        for(int h = 0; h < 3; h++) {
            for(int i = 0; i < 3; i++) {
                String marker = grid[h][i];
                if (marker == "") continue;
                for (int j = -1; j < 2; j++)
                    for (int k = -1; k < 2; k++) {
                        if (h + j >= 0 && i + k >= 0 && h + j < 3 && i + k < 3 && grid[h + j][i + k] == marker) {
                            if (j == 0 && k == 0) continue;
                            int firstPieceCol = h;
                            int firstPieceRow = i;
                            int secondPieceCol = h + j;
                            int secondPieceRow = i + k;
                            int horiDir = -1 * (firstPieceCol - secondPieceCol);
                            int vertDir = -1 * (firstPieceRow - secondPieceRow);
                            innerCol = 0;
                            innerRow = 0;
                            if (secondPieceCol + horiDir > 2 || secondPieceCol + horiDir < 0)
                                innerCol = firstPieceCol - horiDir;
                            else
                                innerCol = secondPieceCol + horiDir;
                            if (secondPieceRow + vertDir > 2 || secondPieceRow + vertDir < 0)
                                innerRow = firstPieceRow - vertDir;
                            else
                                innerRow = secondPieceRow + vertDir;
                            k = 2;
                            j = 2;
                            i = 3;
                            h = 3;

                            break;
                        }
                    }
            }
        }

        System.out.println("R: " + outerRow + " C: " + outerCol + " r: " + innerRow + " c: " + innerCol);
        return new Quartet<Integer, Integer, Integer, Integer>(outerRow, outerCol, innerRow, innerCol);
    }
}

