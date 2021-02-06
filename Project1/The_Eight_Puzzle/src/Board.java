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
            for(int j = 0; j < board.length; j++){
                this.board[i][j] = board[i][j];
            }
        }
        createGoalBoard();
    }

    public Integer[][] getBoard(){
        return this.board;
    }

    public Iterable<Board> expand(ArrayList<Character> moves){
        ArrayList<Board> neighbors = new ArrayList<Board>();
        for(Character el: moves){
            neighbors.add(new Board(swap(el,this.getBoard())));
        }
        return neighbors;
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

    public void printGoalState(){
        for(int i = 0; i < goalBoard.length; i++){
            for(int j = 0 ; j < goalBoard.length; j++){
                System.out.print(goalBoard[i][j]+" ");
            }
            System.out.println();
        }
    }

    public boolean isValid(int x, int y){
        if(x < 0 || y < 0 || x > 2 || y > 2)
            return false;
        return true;
    }

    public ArrayList<Board> neighbors(){
        ArrayList<Board> friends = new ArrayList<Board>();

        char [] moves = {'U','D','L','R'};
        for(int i = 0; i < moves.length; i++){
            Board insertMe = new Board(swap(moves[i], this.board));
            if(insertMe.getBoard() != null)
                friends.add(insertMe);
        }

        return friends;
    }



    public Integer[][] swap(char move, Integer[][] arr){

        Integer[][] nextPuzzle = new Integer[arr.length][arr.length];
        //deep copy
        for(int i = 0 ; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                nextPuzzle[i][j] = arr[i][j];
            }
        }


        for(int i =0; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                if(nextPuzzle[i][j] == 0){
                    switch(move)
                    {
                        case 'U':
                            if(isValid(i-1,j)){
                                System.out.println("Up");
                                Integer temp = nextPuzzle[i-1][j];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i-1][j] = 0;
                                return nextPuzzle;
                            }
                            break;
                        case 'D':
                            if(isValid(i+1,j)){
                                System.out.println("Down");

                                Integer temp = nextPuzzle[i+1][j];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i+1][j] = 0;
                                return nextPuzzle;

                            }
                            break;
                        case 'L':
                            if(isValid(i,j-1)){
                                System.out.println("Left");
                                Integer temp = nextPuzzle[i][j-1];
                                nextPuzzle[i][j] = temp;
                                nextPuzzle[i][j-1] = 0;
                                return nextPuzzle;
                            }
                            break;
                        case 'R':
                            if(isValid(i,j+1)){
                                System.out.println("Right");
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
