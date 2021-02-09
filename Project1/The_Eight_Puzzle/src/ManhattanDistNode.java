public class ManhattanDistNode implements  Comparable<ManhattanDistNode>{

    private int depth;
    private Board b;
    private ManhattanDistNode prev;

    public ManhattanDistNode(ManhattanDistNode parent , Board board, int depth){
        this.b = board;
        this.prev = parent;
        this.depth = depth;
    }

    public ManhattanDistNode getPrev(){
        return this.prev;
    }

    public Integer[][] getGoal(){
        return b.getGoalBoard();
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


    public int manhattan(){
        int val = 1;
        int distance = 0;
        Integer [][] arr = b.getBoard();
        for(int i =0; i < arr.length; i++ ){
            for(int j = 0; j < arr.length; j++){
                if(arr[i][j] != val++ && arr[i][j] != 0)
                {
                    int goalRow = b.findGoalRow(arr[i][j]);
                    int goalCol = b.findGoalCol(arr[i][j]);
                    distance += Math.abs(i - goalRow);
                    distance += Math.abs(j-goalCol);

                }
            }
        }
        return distance;
    }

    @Override
    public int compareTo(ManhattanDistNode o) {
        return 0;
    }
}
