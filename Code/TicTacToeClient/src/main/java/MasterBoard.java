import java.io.Serializable;

public class MasterBoard implements Serializable {
    String[][][][] theBoard = new String[3][3][3][3];
    public boolean[] isWinner = new boolean[9];
    public int[] whoWon = new int[9];
    public boolean endGame = false;

    MasterBoard(){
        for(int i =0; i < 3; i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    for(int z =0; z<3; z++){
                        theBoard[i][j][k][z] = "";
                    }
                }
            }
        }
        for(int i = 0; i < 9; i++){
            isWinner[i] = false;
            whoWon[i] = 0;
        }
    }

    MasterBoard(MasterBoard copyboard){
        for(int i =0; i < 3; i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    for(int z =0; z<3; z++){
                        this.theBoard[i][j][k][z] = copyboard.theBoard[i][j][k][z];
                    }
                }
            }
        }
        for(int i = 0; i < 9; i++){
            isWinner[i] = copyboard.isWinner[i];
            whoWon[i] = copyboard.whoWon[i];
        }
        endGame = copyboard.endGame;
    }


    public void printAll(){
        for(int i =0; i < 3; i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    for(int z =0; z<3; z++){
                        if(z!= 2)
                            System.out.print(" | " + theBoard[i][j][k][z] + " | ");
                        else
                            System.out.println(" | " + theBoard[i][j][k][z] + " | ");
                    }
                }
            }
        }
    }


}
