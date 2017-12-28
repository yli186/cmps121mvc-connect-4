package cs121.ucsc.assignment2;



import java.util.Observable;

/**
 * Created by user on 10/29/2017.
 */
//board , model
    //store all current state of the board
public class Model extends Observable{
    static String[] functions = {"New Game", "Load Game"};
    public static final int num_col = 7;
    public static final int num_row =6;
    public static int[][] board =  new int[6][7];

    public Model() {}
    //1 = green, 2 = red 's turn


    public void putDisk(int colIdx, int turn){


            //check elements in the column
            for (int row = 5; row>=0; row--) {
                if (board[row][colIdx] == 0) {
                    if(turn == 0){
                        board[row][colIdx] = 2;
                        break;
                    }
                    else{
                        board[row][colIdx] = turn;
                        break;
                    }
                }

            }


    }
    private static int getWinnerInRows(int[][] field) {

        int countG = 0;
        int countR = 0;
        for (int row = 0; row < 6; row++) {

            for (int column = 0; column < 7; column++) {
                if(field[row][column]==1){
                    countG++;
                }
                else{
                    countG =0;
                }

                if(field[row][column]==2){
                    countR++;
                }
                else{
                    countR=0;
                }

                if (countG >= 4 || countR >=4) {

                    return field[row][column];
                }
            }
        }

        return 0;
    }
    private static int getWinnerInCol(int[][] field){
        int countG = 0;
        int countR = 0;
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                if(field[row][col]==1){
                    countG++;
                }
                else{
                    countG =0;
                }

                if(field[row][col]==2){
                    countR++;
                }
                else{
                    countR=0;
                }

                if (countG >= 4 || countR >=4) {

                    return field[row][col];
                }
            }
        }
        return 0;
    }
    private static int getWinnerInDiag(int[][] field){

        for (int i=3; i<6; i++){
            for (int j=0; j<7-3; j++){
                if (field[i][j] == 1 && field[i-1][j+1] == 1 && field[i-2][j+2] == 1 && field[i-3][j+3] == 1)
                    return 1;
            }
        }
        for (int i=3; i<6; i++){
            for (int j=0; j<7-3; j++){
                if (field[i][j] == 2 && field[i-1][j+1] == 2 && field[i-2][j+2] == 2 && field[i-3][j+3] == 2)
                    return 2;
            }
        }
        for (int i=3; i<6; i++){
            for (int j=3; j<7; j++){
                if (field[i][j] == 1 && field[i-1][j-1] == 1 && field[i-2][j-2] == 1 && field[i-3][j-3] == 1)
                    return 1;
            }
        }
        for (int i=3; i<6; i++){
            for (int j=3; j<7; j++){
                if (field[i][j] ==2 && field[i-1][j-1] == 2 && field[i-2][j-2] ==2 && field[i-3][j-3] == 2)
                    return 2;
            }
        }
        return 0;
    }
    public static int getWinner() {
        int winner = getWinnerInRows(board);
        if (winner !=0 ) {
            return winner;
        }
       winner = getWinnerInCol(board);
        if (winner !=0) return winner;
        winner = getWinnerInDiag(board);
        if (winner !=0) return winner;

        for (int i = 0; i < board.length; ++i)
            for (int j = 0; j < board[i].length; ++j)
                if (board[i][j] == 0) return 0;

        return 3;
    }



}
