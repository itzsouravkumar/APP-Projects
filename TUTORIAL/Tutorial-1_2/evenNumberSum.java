import java.util.Scanner;

public class evenNumberSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int sum = 0;
        for (int i = 2; i <= number; i += 2) {
            sum += i;
        }

        System.out.println("Sum of even numbers from 1 to " + number + " is: " + sum);
        scanner.close();
    }
}
