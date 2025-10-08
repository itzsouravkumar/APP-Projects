class OddPrinter extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 20; i += 2) {
            System.out.println("Odd: " + i);
            try {
                Thread.sleep(300); // small delay to simulate concurrency
            } catch (InterruptedException e) {
                System.out.println("Odd thread interrupted.");
            }
        }
    }
}

class EvenPrinter extends Thread {
    @Override
    public void run() {
        for (int i = 2; i <= 20; i += 2) {
            System.out.println("Even: " + i);
            try {
                Thread.sleep(300); // small delay to simulate concurrency
            } catch (InterruptedException e) {
                System.out.println("Even thread interrupted.");
            }
        }
    }
}

public class oddEvenThreads {
    public static void main(String[] args) {
        OddPrinter oddThread = new OddPrinter();
        EvenPrinter evenThread = new EvenPrinter();

        oddThread.start();
        evenThread.start();
    }
}
