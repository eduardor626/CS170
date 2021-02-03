import java.util.Scanner;  // Import the Scanner class

public class Driver {


    public static int welcomeMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Eduardo Rocha's 8-Puzzle Solver");
        System.out.println("Type “1” to use a default puzzle, or “2” to enter your own puzzle.");
        int option = input.nextInt();
        input.nextLine();
        System.out.println("Enter your puzzle, use a zero to represent the blank");
        System.out.println("Enter the first row, use space or tabs between numbers: ");
        String firstRow = input.nextLine();
        System.out.println("Enter the second row, use space or tabs between numbers: ");
        String secondRow = input.nextLine();
        System.out.println("Enter the third row, use space or tabs between numbers: ");
        String thirdRow = input.nextLine();
        System.out.println("--- Puzzle ---");
        System.out.println(firstRow+"\n"+secondRow+"\n"+thirdRow);

        return 0;
    }
    

    public static void main(String[] args) {
        welcomeMessage();

    }
}
