import java.util.ArrayList;
import java.util.PriorityQueue;

public class MisplacedTileNode implements  Comparable<MisplacedTileNode>{

    private int depth;
    private Board b;
    private MisplacedTileNode prev;

    public MisplacedTileNode(MisplacedTileNode parent , Board board, int depth){
        this.b = board;
        this.prev = parent;
        this.depth = depth;
    }

    public MisplacedTileNode getPrev(){
        return this.prev;
    }

    public Board get(){
        return b;
    }

    public boolean isSolved(){
        return b.goalTest();
    }

    public int getDepth(){
        return this.depth;
    }

    public int getMisplaced(){
        int val = 1;
        int misplaced = 0;
        Integer[][] check = b.getBoard();
        for(int i = 0 ; i < check.length; i++){
            for(int j = 0; j < check.length; j++) {
                if(check[i][j] != val++){
                    misplaced++;
                }
            }
        }
        return misplaced-1;
    }




    @Override
    public int compareTo(MisplacedTileNode other) {
        return Integer.compare(this.getDepth()+this.getMisplaced(), other.getDepth()+other.getMisplaced());
    }
}
