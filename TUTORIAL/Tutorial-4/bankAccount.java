public class bankAccount {
    String accHolderName;
    double balance;

    public bankAccount(String name, double initialBalance) {
        accHolderName = name;
        balance = initialBalance;
    }
    // deposit method
    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }
    // withdraw method
    public void withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdraw amount.");
        }
    }

    // display method
    public void displayAccountInfo() {
        System.out.println("Account Holder: " + accHolderName);
        System.out.println("Balance: " + balance);
    }

    public static void main(String[] args) {
        //multiple objects
        bankAccount acc1 = new bankAccount("Vihan", 1000);
        bankAccount acc2 = new bankAccount("Varun", 500);
        bankAccount acc3 = new bankAccount("Aditi", 1500);

        acc1.deposit(200);
        acc1.withdraw(100);
        acc1.displayAccountInfo();
        System.out.print("-".repeat(30) + "\n");

        acc2.deposit(300);
        acc2.withdraw(700);
        acc2.displayAccountInfo();
        System.out.print("-".repeat(30) + "\n");

        acc3.deposit(500);
        acc3.withdraw(200);
        acc3.displayAccountInfo();
    }
}
