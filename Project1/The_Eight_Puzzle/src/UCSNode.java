import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class UCSNode  implements  Comparable<UCSNode>{

    private Stack<Board> moves;
    private int depth;
    private Board b;
    private UCSNode prev;

    public UCSNode(UCSNode parent , Board board, int depth){
        this.b = board;
        this.prev = parent;
        this.depth = depth;
    }

    public UCSNode getPrev(){
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

    @Override
    public int compareTo(UCSNode otherNode) {
        return Integer.compare(this.depth,otherNode.depth);
    }
}