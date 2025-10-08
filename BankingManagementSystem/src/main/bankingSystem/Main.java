package main.bankingSystem;

import main.bankingSystem.accounts.BankAccount;
import main.bankingSystem.accounts.FixedDepositAccount;
import main.bankingSystem.management.BankManager;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BankManager bankManager = new BankManager();

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     🏦 WELCOME TO PROFESSIONAL BANKING SYSTEM 🏦");
        System.out.println("=".repeat(60));

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> createAccountMenu();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> transferMoney();
                case 5 -> checkBalance();
                case 6 -> viewAccountDetails();
                case 7 -> viewTransactionHistory();
                case 8 -> calculateInterest();
                case 9 -> accountManagementMenu();
                case 10 -> bankManager.displayAllAccounts();
                case 11 -> bankManager.generateBankReport();
                case 0 -> {
                    System.out.println("\n✅ Thank you for using our banking system!");
                    running = false;
                }
                default -> System.out.println("❌ Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("1.  Create New Account");
        System.out.println("2.  Deposit Money");
        System.out.println("3.  Withdraw Money");
        System.out.println("4.  Transfer Money");
        System.out.println("5.  Check Balance");
        System.out.println("6.  View Account Details");
        System.out.println("7.  View Transaction History");
        System.out.println("8.  Calculate Interest");
        System.out.println("9.  Account Management");
        System.out.println("10. View All Accounts");
        System.out.println("11. Generate Bank Report");
        System.out.println("0.  Exit");
        System.out.println("=".repeat(60));
    }

    private static void createAccountMenu() {
        System.out.println("\n--- Create New Account ---");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Fixed Deposit Account");
        int choice = getIntInput("Select account type: ");

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        double initialDeposit = getDoubleInput("Enter initial deposit: ₹");

        try {
            switch (choice) {
                case 1 -> bankManager.createSavingsAccount(name, initialDeposit);
                case 2 -> {
                    double overdraft = getDoubleInput("Enter overdraft limit: ₹");
                    bankManager.createCurrentAccount(name, initialDeposit, overdraft);
                }
                case 3 -> {
                    int tenure = getIntInput("Enter tenure (months): ");
                    bankManager.createFixedDepositAccount(name, initialDeposit, tenure);
                }
                default -> System.out.println("❌ Invalid account type!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void depositMoney() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            double amount = getDoubleInput("Enter deposit amount: ₹");
            account.deposit(amount);
            System.out.println("New Balance: ₹" + String.format("%.2f", account.getBalance()));
        }
    }

    private static void withdrawMoney() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            double amount = getDoubleInput("Enter withdrawal amount: ₹");
            account.withdraw(amount);
            System.out.println("New Balance: ₹" + String.format("%.2f", account.getBalance()));
        }
    }

    private static void transferMoney() {
        System.out.print("\nEnter source account number: ");
        String srcAccNum = scanner.nextLine();
        BankAccount srcAccount = bankManager.getAccount(srcAccNum);

        if (srcAccount != null) {
            System.out.print("Enter destination account number: ");
            String destAccNum = scanner.nextLine();
            BankAccount destAccount = bankManager.getAccount(destAccNum);

            if (destAccount != null) {
                double amount = getDoubleInput("Enter transfer amount: ₹");
                srcAccount.transfer(destAccount, amount);
            }
        }
    }

    private static void checkBalance() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            System.out.println("\n💰 Current Balance: ₹" +
                    String.format("%.2f", account.getBalance()));
        }
    }

    private static void viewAccountDetails() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            account.displayAccountInfo();
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            account.displayTransactionHistory();
        }
    }

    private static void calculateInterest() {
        System.out.print("\nEnter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            if (account instanceof FixedDepositAccount) {
                ((FixedDepositAccount) account).checkMaturity();
            } else {
                account.calculateInterest();
            }
            System.out.println("New Balance: ₹" +
                    String.format("%.2f", account.getBalance()));
        }
    }

    private static void accountManagementMenu() {
        System.out.println("\n--- Account Management ---");
        System.out.println("1. Close Account");
        System.out.println("2. Reactivate Account");
        System.out.println("3. Delete Account");
        int choice = getIntInput("Select option: ");

        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount account = bankManager.getAccount(accNum);

        if (account != null) {
            switch (choice) {
                case 1 -> account.closeAccount();
                case 2 -> account.reactivateAccount();
                case 3 -> bankManager.deleteAccount(accNum);
                default -> System.out.println("❌ Invalid option!");
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value < 0) {
                    System.out.println("❌ Amount cannot be negative!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid amount.");
            }
        }
    }
}