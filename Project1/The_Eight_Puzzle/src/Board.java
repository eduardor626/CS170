import java.util.ArrayList;

public class Board {
    private Integer [][] board = new Integer[3][3];
    private int priority = Integer.MAX_VALUE;
    private int depth;
    private Integer[][] goalBoard = new Integer[3][3];

    public Board(ArrayList<String> puzzle){
        for(int i = 0 ; i < puzzle.size(); i++){
            for(int j = 0; j < puzzle.size(); j++){
                board[i][j] = Character.getNumericValue(puzzle.get(i).charAt(j));
            }
        }
        createGoalBoard();
    }

    public void createGoalBoard(){

    }



    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isValid(int x, int y){
        if(x < 0 || y < 0 || x > 2 || y > 2)
            return false;
        return true;
    }

    public void swap(char operator){
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                Integer temp;
                if(board[i][j] == 0 ){
                   switch(operator)
                   {
                       case 'U':
                           if(isValid(i-1,j)){
                               temp = board[i-1][j];
                               board[i][j] = temp;
                               board[i-1][j] = 0;
                           }
                           break;
                       case 'D':
                           if(isValid(i+1,j)){
                               temp = board[i+1][j];
                               board[i][j] = temp;
                               board[i+1][j] = 0;
                           }
                           break;
                       case 'L':
                           if(isValid(i,j-1)){
                               temp = board[i][j-1];
                               board[i][j] = temp;
                               board[i][j-1] = 0;
                           }
                           break;
                       case 'R':
                           if(isValid(i,j+1)){
                               temp = board[i][j+1];
                               board[i][j] = temp;
                               board[i][j+1] = 0;
                           }
                           break;
                   }
                   return;
                }
            }
        }
    }

    public void print() {
       for(int i = 0; i < board.length; i++){
           for(int j = 0 ; j < board.length; j++){
               System.out.print(board[i][j]+" ");
           }
           System.out.println();
       }
    }


}
