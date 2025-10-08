import java.util.Scanner;

public class evenNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("Even numbers from 1 to " + number + " are:");
        for (int i = 2; i <= number; i += 2) {
            System.out.print(i + " ");
        }
        scanner.close();
    }
}
