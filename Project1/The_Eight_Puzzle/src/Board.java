import java.util.ArrayList;

public class Board {
    private Integer [][] board = new Integer[3][3];
    private int priority = Integer.MAX_VALUE;
    private int depth;

    public Board(ArrayList<String> puzzle){
        for(int i = 0 ; i < puzzle.size(); i++){
            for(int j = 0; j < puzzle.size(); j++){
                board[i][j] = Character.getNumericValue(puzzle.get(i).charAt(j));
            }
        }
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


}
