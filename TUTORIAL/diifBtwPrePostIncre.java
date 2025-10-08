package TUTORIAL;
import java.util.Scanner;
public class diifBtwPrePostIncre {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        // Pre-increment
        int preIncrement = ++num;
        System.out.println("Pre-increment: " + preIncrement);

        // Post-increment
        int postIncrement = num++;
        System.out.println("Post-increment: " + postIncrement);

        int result = ++num + num++;
        System.out.println("Result of pre-increment and post-increment: " + result);

        sc.close();
    }
}
