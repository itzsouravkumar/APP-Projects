package main.bankingSystem.accounts;

import bankingsystem.transactions.Transaction;
import bankingsystem.transactions.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;
    private final LocalDateTime createdDate;
    private final List<Transaction> transactionHistory;
    private boolean isActive;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.createdDate = LocalDateTime.now();
        this.transactionHistory = new ArrayList<>();
        this.isActive = true;

        if (initialBalance > 0) {
            addTransaction(new Transaction(TransactionType.DEPOSIT, initialBalance,
                    "Initial deposit"));
        }
    }

    // Abstract methods to be implemented by subclasses
    public abstract String getAccountType();
    public abstract double getInterestRate();
    public abstract double getMinimumBalance();

    public synchronized boolean deposit(double amount) {
        if (!isActive) {
            System.out.println("❌ Account is inactive!");
            return false;
        }
        if (amount <= 0) {
            System.out.println("❌ Deposit amount must be positive!");
            return false;
        }

        balance += amount;
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount,
                "Deposit to account"));
        System.out.println("✅ Successfully deposited ₹" + String.format("%.2f", amount));
        return true;
    }

    // Synchronized withdrawal method for thread safety
    public synchronized boolean withdraw(double amount) {
        if (!isActive) {
            System.out.println("❌ Account is inactive!");
            return false;
        }
        if (amount <= 0) {
            System.out.println("❌ Withdrawal amount must be positive!");
            return false;
        }
        if (balance - amount < getMinimumBalance()) {
            System.out.println("❌ Insufficient balance! Minimum balance: ₹" +
                    String.format("%.2f", getMinimumBalance()));
            return false;
        }

        balance -= amount;
        addTransaction(new Transaction(TransactionType.WITHDRAWAL, amount,
                "Withdrawal from account"));
        System.out.println("✅ Successfully withdrew ₹" + String.format("%.2f", amount));
        return true;
    }

    // Synchronized transfer (atomic from caller perspective)
    public synchronized boolean transfer(BankAccount targetAccount, double amount) {
        if (!isActive) {
            System.out.println("❌ Source account is inactive!");
            return false;
        }
        if (amount <= 0) {
            System.out.println("❌ Transfer amount must be positive!");
            return false;
        }
        if (balance - amount < getMinimumBalance()) {
            System.out.println("❌ Insufficient balance for transfer!");
            return false;
        }

        // Deduct from source and credit to target
        balance -= amount;
        // note: accessing private field of another instance of same class is permitted in Java
        targetAccount.balance += amount;

        addTransaction(new Transaction(TransactionType.TRANSFER_OUT, amount,
                "Transfer to " + targetAccount.getAccountNumber()));
        targetAccount.addTransaction(new Transaction(TransactionType.TRANSFER_IN, amount,
                "Transfer from " + this.accountNumber));

        System.out.println("✅ Successfully transferred ₹" + String.format("%.2f", amount) +
                " to " + targetAccount.getAccountNumber());
        return true;
    }

    public void calculateInterest() {
        double interest = balance * (getInterestRate() / 100);
        if (interest > 0) {
            balance += interest;
            addTransaction(new Transaction(TransactionType.INTEREST, interest,
                    "Interest credit"));
            System.out.println("✅ Interest credited: ₹" + String.format("%.2f", interest));
        }
    }

    private void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void displayAccountInfo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    ACCOUNT DETAILS");
        System.out.println("=".repeat(60));
        System.out.println("Account Type     : " + getAccountType());
        System.out.println("Account Number   : " + accountNumber);
        System.out.println("Account Holder   : " + accountHolder);
        System.out.println("Current Balance  : ₹" + String.format("%.2f", balance));
        System.out.println("Minimum Balance  : ₹" + String.format("%.2f", getMinimumBalance()));
        System.out.println("Interest Rate    : " + getInterestRate() + "%");
        System.out.println("Account Status   : " + (isActive ? "Active" : "Inactive"));
        System.out.println("Created Date     : " + createdDate.format(DATE_FORMATTER));
        System.out.println("=".repeat(60));
    }

    public void displayTransactionHistory() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                         TRANSACTION HISTORY");
        System.out.println("=".repeat(80));
        System.out.printf("%-20s %-15s %-15s %-30s%n",
                "Date & Time", "Type", "Amount", "Description");
        System.out.println("-".repeat(80));

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactionHistory) {
                System.out.printf("%-20s %-15s ₹%-14.2f %-30s%n",
                        t.getTimestamp().format(DATE_FORMATTER),
                        t.getType(),
                        t.getAmount(),
                        t.getDescription());
            }
        }
        System.out.println("=".repeat(80));
    }

    public void closeAccount() {
        this.isActive = false;
        System.out.println("✅ Account " + accountNumber + " has been closed.");
    }

    public void reactivateAccount() {
        this.isActive = true;
        System.out.println("✅ Account " + accountNumber + " has been reactivated.");
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}