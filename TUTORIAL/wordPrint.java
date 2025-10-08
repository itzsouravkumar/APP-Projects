package TUTORIAL;
import java.util.Scanner;
public class wordPrint {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String sentence = sc.nextLine();
        String[] words = sentence.split(" ");
        for (String word : words) {
            System.out.println(word);
        }
        sc.close();
    }
}
