
public class UCSNode  implements  Comparable<UCSNode>{

    private int depth;
    private Board b;
    private UCSNode prev;

    public UCSNode(UCSNode parent , Board board, int depth){
        this.b = board;
        this.prev = parent;
        this.depth = depth;
    }

    public Board get(){
        return b;
    }

    public boolean isSolved(){
        return b.goalTest();
    }


    @Override
    public int compareTo(UCSNode otherNode) {
        return Integer.compare(this.depth,otherNode.depth);
    }
}