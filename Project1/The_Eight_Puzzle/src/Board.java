import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private Integer [][] board = new Integer[3][3];
    private int depth = Integer.MAX_VALUE;
    private Integer[][] goalBoard = new Integer[3][3];

    public Board(ArrayList<String> puzzle){
        for(int i = 0 ; i < puzzle.size(); i++){
            for(int j = 0; j < puzzle.size(); j++){
                board[i][j] = Character.getNumericValue(puzzle.get(i).charAt(j));
            }
        }
        createGoalBoard();
        depth = 0;
    }

    public Board(Integer [][] board){
        for(int i = 0 ; i < board.length; i++){
            System.arraycopy(board[i], 0, this.board[i], 0, board.length);
        }
        createGoalBoard();
    }

    public Integer[][] getBoard(){
        return this.board;
    }

    public void createGoalBoard(){
        int val = 1;
        for(int i = 0 ; i < goalBoard.length; i++){
            for(int j = 0; j < goalBoard.length; j++) {
                this.goalBoard[i][j] = val++;
            }
        }
        this.goalBoard[2][2] = 0;
    }

    public Integer[][] getGoalBoard(){
        return this.goalBoard;
    }

    public int findGoalRow(int value){
        for(int i =0; i < goalBoard.length; i++){
            for(int j = 0; j < goalBoard.length; j++){
                if(goalBoard[i][j] == value)
                    return i;
            }
        }
        return -1;
    }

    public int findGoalCol(int value){
        for(int i =0; i < goalBoard.length; i++){
            for(int j = 0; j < goalBoard.length; j++){
                if(goalBoard[i][j] == value)
                    return j;
            }
        }
        return -1;
    }

    public void printGoalState(){
        for (Integer[] integers : goalBoard) {
            for (int j = 0; j < goalBoard.length; j++) {
                System.out.print(integers[j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValid(int x, int y){
        return x >= 0 && y >= 0 && x <= 2 && y <= 2;
    }

    public ArrayList<Board> neighbors(){
        ArrayList<Board> friends = new ArrayList<Board>();

        char [] moves = {'U','D','L','R'};
        for (char move : moves) {
            Board insertMe = new Board(swap(move, this.board));
            if (!Arrays.deepEquals(insertMe.getBoard(), this.board))
                friends.add(insertMe);
        }
        return friends;
    }



    public Integer[][] swap(char move, Integer[][] arr){

        Integer[][] nextPuzzle = new Integer[arr.length][arr.length];
        //deep copy
        for(int i = 0 ; i < arr.length; i++){
            System.arraycopy(arr[i], 0, nextPuzzle[i], 0, arr.length);
        }


        for(int i =0; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                if(nextPuzzle[i][j] == 0){
                    switch(move)
                    {
                        case 'U':
                            if(isValid(i-1,j)){
                                Integer temp = nextPuzzle[i-1][j];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i-1][j] = 0;
                                return nextPuzzle;
                            }
                            break;
                        case 'D':
                            if(isValid(i+1,j)){
                                Integer temp = nextPuzzle[i+1][j];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i+1][j] = 0;
                                return nextPuzzle;

                            }
                            break;
                        case 'L':
                            if(isValid(i,j-1)){
                                Integer temp = nextPuzzle[i][j-1];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i][j-1] = 0;
                                return nextPuzzle;
                            }
                            break;
                        case 'R':
                            if(isValid(i,j+1)){
                                Integer temp = nextPuzzle[i][j+1];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i][j+1] = 0;
                                return nextPuzzle;
                            }
                            break;
                        default:
                            break;
                    }
                    return nextPuzzle;
                }
            }
        }
        return nextPuzzle;

    }

    public int getDepth() {
        return depth;
    }

    // is current state of board equal to the goal state of the board
    public boolean goalTest(){
        return Arrays.deepEquals(this.board, this.goalBoard);
    }

    public void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
