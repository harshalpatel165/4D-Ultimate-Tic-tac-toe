public class GameLogic {

    // this function checks a specific grid in the grid array and
    // if there are 3 in a row it returns true
    //

    public boolean isPointScored2(MasterBoard masterBoard,BoardInfo board,int Ipiece){
        String piece = Integer.toString(Ipiece);
        int outerRow = board.outerRow;
        int outerCol = board.outerCol;
        int row = board.row;
        int col = board.col;

        if(row == 0 && col == 0){
            if(masterBoard.theBoard[outerRow][outerCol][0][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][0][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece))
                return true;
        }
        else if(row == 1 && col == 0){
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][2].equals(piece))
                return true;
        }
        else if(row == 2 && col == 0){
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][0].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][2][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece))
                return true;
        }
        else if(row == 0 && col == 1){
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][0][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][1].equals(piece))
                return true;
        }
        else if(row == 1 && col == 1){
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][0][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][1].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][1][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][0][2].equals(piece))
                return true;
        }
        else if(row == 2 && col == 1){
            if(masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][0][1].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece))
                return true;
        }
        else if(row == 0 && col == 2){
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][0][1].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][2].equals(piece))
                return true;
        }
        else if(row == 1 && col == 2){
            if(masterBoard.theBoard[outerRow][outerCol][1][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][0][2].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][2].equals(piece))
                return true;
        }
        else if(row == 2 && col == 2){
            if(masterBoard.theBoard[outerRow][outerCol][0][2].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][2].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][0][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][1][1].equals(piece))
                return true;
            if(masterBoard.theBoard[outerRow][outerCol][2][0].equals(piece) && masterBoard.theBoard[outerRow][outerCol][2][1].equals(piece))
                return true;
        }
        return false;
    }

    //
    // This function decides if the board is a winner
    //
    public boolean isWinner2(MasterBoard masterBoard){
        if(masterBoard.isWinner[0] && masterBoard.isWinner[1] && masterBoard.isWinner[2])
            if(masterBoard.whoWon[0] == masterBoard.whoWon[1] && masterBoard.whoWon[0] == masterBoard.whoWon[2])
                return true;
        if(masterBoard.isWinner[3] && masterBoard.isWinner[4] && masterBoard.isWinner[5])
            if(masterBoard.whoWon[3] == masterBoard.whoWon[4] && masterBoard.whoWon[3] == masterBoard.whoWon[5])
                return true;
        if(masterBoard.isWinner[6] && masterBoard.isWinner[7] && masterBoard.isWinner[8])
            if(masterBoard.whoWon[6] == masterBoard.whoWon[7] && masterBoard.whoWon[6] == masterBoard.whoWon[8])
                return true;
        if(masterBoard.isWinner[0] && masterBoard.isWinner[3] && masterBoard.isWinner[6])
            if(masterBoard.whoWon[0] == masterBoard.whoWon[3] && masterBoard.whoWon[0] == masterBoard.whoWon[6])
                return true;
        if(masterBoard.isWinner[1] && masterBoard.isWinner[4] && masterBoard.isWinner[7])
            if(masterBoard.whoWon[1] == masterBoard.whoWon[4] && masterBoard.whoWon[1] == masterBoard.whoWon[7])
                return true;
        if(masterBoard.isWinner[2] && masterBoard.isWinner[5] && masterBoard.isWinner[8])
            if(masterBoard.whoWon[2] == masterBoard.whoWon[5] && masterBoard.whoWon[2] == masterBoard.whoWon[8])
                return true;
        if(masterBoard.isWinner[0] && masterBoard.isWinner[4] && masterBoard.isWinner[8])
            if(masterBoard.whoWon[0] == masterBoard.whoWon[4] && masterBoard.whoWon[0] == masterBoard.whoWon[8])
                return true;
        if(masterBoard.isWinner[2] && masterBoard.isWinner[4] && masterBoard.isWinner[6])
            if(masterBoard.whoWon[2] == masterBoard.whoWon[4] && masterBoard.whoWon[2] == masterBoard.whoWon[6])
                return true;

        return false;
    }
}