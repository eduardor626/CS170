import java.util.PriorityQueue;

public interface QueueingFunction {

    // Method that will overwritten for each class
    public PriorityQueue<QueueingFunction> enqueue();
}
