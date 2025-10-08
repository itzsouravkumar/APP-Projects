package TUTORIAL;
import java.util.Scanner;

public class votingEligibility {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        if(age < 18 && age > 0) {
            System.out.println("You are not eligible to vote.");
        } else if(age >= 18 && age <= 100) {
            System.out.println("You are eligible to vote.");
        } else {
            System.out.println("Invalid age.");
            sc.close();
        }
    }
}
