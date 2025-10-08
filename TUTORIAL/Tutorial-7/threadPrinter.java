class NumberPrinter extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

public class threadPrinter {
    public static void main(String[] args) {
        NumberPrinter thread = new NumberPrinter();
        thread.start();
    }
}
