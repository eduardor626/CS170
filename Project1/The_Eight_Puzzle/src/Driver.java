import java.util.*;

public class Driver {

    private static Stack<Board> moves = new Stack<>();
    private static int maxQueueSize = 0;

    public static int whichAlgorithm(){
        System.out.println("Enter your choice of algorithm");
        System.out.println("1. Uniform Cost Search");
        System.out.println("2. A* with the Misplaced Tile heuristic");
        System.out.println("3. A* with the Manhattan distance heuristic");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        return choice;
    }

    public static boolean validInput(ArrayList<String> puzzle){
        Set<Integer> valid = new HashSet<Integer>();
        if(puzzle.size() != 3)
            return false;
        for(int i = 0; i < puzzle.size(); i++){
            for(int j = 0; j < puzzle.size(); j++){
                int val = Character.getNumericValue((puzzle.get(i).charAt(j)));
                if(valid.contains(val) || val < 0 || val > 8)
                    return false;
                valid.add(val);
            }

        }
        return true;
    }

    public static ArrayList<String> getInput(){
        Scanner input = new Scanner(System.in);
        ArrayList<String> puzzle = new ArrayList<String>();
        System.out.println("Enter your puzzle, use a zero to represent the blank");
        System.out.println("Enter the first row, use space or tabs between numbers: ");
        String firstRow = input.nextLine();
        firstRow = firstRow.replaceAll("\\s", "");
        System.out.println("Enter the second row, use space or tabs between numbers: ");
        String secondRow = input.nextLine();
        secondRow = secondRow.replaceAll("\\s", "");
        System.out.println("Enter the third row, use space or tabs between numbers: ");
        String thirdRow = input.nextLine();
        thirdRow = thirdRow.replaceAll("\\s", "");
        puzzle.add(firstRow);
        puzzle.add(secondRow);
        puzzle.add(thirdRow);
        if(!validInput(puzzle)) {
            System.out.println("Invalid Input! REDO from beginning function needed!");
            return new ArrayList<String>();
        }
        return puzzle;
    }


    /*
    The head of the priority queue is the least element based on the natural ordering
    or comparator based ordering.
     */
    public static boolean general_search(UCSNode n){
        PriorityQueue<UCSNode> nodes = new PriorityQueue<UCSNode>();
        ArrayList<Board> friends = new ArrayList<>();
        nodes.add(n);
        while(!nodes.isEmpty())
        {
            UCSNode node = nodes.remove();
            if (node.isSolved()){
                System.out.println("Depth: "+node.getDepth());
                System.out.println("Maximum number of nodes in the queue: "+maxQueueSize);

                moves.add(node.get());
                UCSNode prev = node.getPrev();
                while(prev != null){
                    moves.add(prev.get());
                    prev = prev.getPrev();
                }

                return true;

            }
            friends = new ArrayList<>();
            friends = node.get().neighbors();
            for(Board el : friends){
                nodes.add(new UCSNode(node,el,node.getDepth()+1));
            }
            maxQueueSize = Math.max(maxQueueSize,nodes.size());

        }
        return false;
    }

    public static boolean general_search_misplaced(MisplacedTileNode n){
        PriorityQueue<MisplacedTileNode> nodes = new PriorityQueue<MisplacedTileNode>();
        ArrayList<Board> friends = new ArrayList<>();
        nodes.add(n);
        while(!nodes.isEmpty())
        {
            MisplacedTileNode node = nodes.remove();
            if (node.isSolved()){
                System.out.println("Depth: "+node.getDepth());
                System.out.println("Maximum number of nodes in the queue: "+maxQueueSize);
                moves.add(node.get());
                MisplacedTileNode prev = node.getPrev();
                while(prev != null){
                    moves.add(prev.get());
                    prev = prev.getPrev();
                }
                return true;
            }
            friends = new ArrayList<>();
            friends = node.get().neighbors();
            for(Board el : friends){
                nodes.add(new MisplacedTileNode(node,el,node.getDepth()+1));
            }
            maxQueueSize = Math.max(maxQueueSize,nodes.size());
        }
        return false;
    }

    public static int createAlgorithm(int algorithm, Board b){

        switch(algorithm)
        {
            case 1:
                System.out.println("Uniform Cost Search");
                UCSNode uniformCostNode = new UCSNode(null,b,0);
                System.out.println(general_search(uniformCostNode));
                return 1;
            case 2:
                System.out.println("A* w/ Misplaced Tile Search");
                MisplacedTileNode misplaced = new MisplacedTileNode(null,b,0);
                System.out.println(general_search_misplaced(misplaced));
                return 2;
            case 3:
                System.out.println("A* w/ Manhattan Distance Search");
                ManhattanDistNode manhattan = new ManhattanDistNode(null,b,0);
                System.out.println("manhattan dist = "+manhattan.manhattan());
                return 3;
            default:
                System.out.println("Error: Invalid Algorithm");
                break;
        }
        return -1;
    }

    public static int welcomeMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Eduardo Rocha's 8-Puzzle Solver");
        System.out.println("Type “1” to use a default puzzle, or “2” to enter your own puzzle.");
        int option = input.nextInt();
        input.nextLine();
        ArrayList<String> userPuzzle = new ArrayList<String>(getInput());

        if(!userPuzzle.isEmpty()){
            Board slidingPuzzle = new Board(userPuzzle);

            int algorithm = whichAlgorithm();
            return createAlgorithm(algorithm, slidingPuzzle);

        }else
        {
            return -1;
        }

    }

    public static void main(String[] args) {
        welcomeMessage();

        while(!moves.isEmpty()){
            System.out.println("-- Next Board --");
            moves.pop().print();
        }

    }
}
