package main.bankingSystem.management;

import main.bankingSystem.accounts.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BankManager {
    private final Map<String, BankAccount> accounts;
    private int accountCounter;

    public BankManager() {
        this.accounts = new ConcurrentHashMap<>();
        this.accountCounter = 1000;
    }

    public String createSavingsAccount(String holderName, double initialDeposit) {
        String accountNumber = "SAV" + (++accountCounter);
        BankAccount account = new SavingsAccount(accountNumber, holderName, initialDeposit);
        accounts.put(accountNumber, account);
        System.out.println("✅ Savings Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        return accountNumber;
    }

    public String createCurrentAccount(String holderName, double initialDeposit,
            double overdraftLimit) {
        String accountNumber = "CUR" + (++accountCounter);
        BankAccount account = new CurrentAccount(accountNumber, holderName,
                initialDeposit, overdraftLimit);
        accounts.put(accountNumber, account);
        System.out.println("✅ Current Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        return accountNumber;
    }

    public String createFixedDepositAccount(String holderName, double depositAmount,
            int tenureMonths) {
        String accountNumber = "FD" + (++accountCounter);
        BankAccount account = new FixedDepositAccount(accountNumber, holderName,
                depositAmount, tenureMonths);
        accounts.put(accountNumber, account);
        System.out.println("✅ Fixed Deposit Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        return accountNumber;
    }

    public BankAccount getAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("❌ Account not found!");
        }
        return account;
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the system.");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                  ALL ACCOUNTS");
        System.out.println("=".repeat(100));
        System.out.printf("%-15s %-20s %-25s %-15s %-12s%n",
                "Account No", "Type", "Holder", "Balance", "Status");
        System.out.println("-".repeat(100));

        for (BankAccount account : accounts.values()) {
            System.out.printf("%-15s %-20s %-25s ₹%-14.2f %-12s%n",
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getAccountHolder(),
                    account.getBalance(),
                    account.isActive() ? "Active" : "Inactive");
        }
        System.out.println("=".repeat(100));
    }

    public void generateBankReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                     BANK REPORT");
        System.out.println("=".repeat(60));

        long totalAccounts = accounts.size();
        long activeAccounts = accounts.values().stream()
                .filter(BankAccount::isActive).count();
        double totalBalance = accounts.values().stream()
                .mapToDouble(BankAccount::getBalance).sum();

        Map<String, Long> accountTypeCount = accounts.values().stream()
                .collect(Collectors.groupingBy(BankAccount::getAccountType,
                        Collectors.counting()));

        System.out.println("Total Accounts     : " + totalAccounts);
        System.out.println("Active Accounts    : " + activeAccounts);
        System.out.println("Total Bank Balance : ₹" + String.format("%.2f", totalBalance));
        System.out.println("\nAccount Types:");
        accountTypeCount.forEach((type, count) -> System.out.println("  " + type + ": " + count));
        System.out.println("=".repeat(60));
    }

    public boolean deleteAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("❌ Account not found!");
            return false;
        }
        if (account.getBalance() > 0) {
            System.out.println("❌ Cannot delete account with positive balance!");
            return false;
        }
        accounts.remove(accountNumber);
        System.out.println("✅ Account deleted successfully!");
        return true;
    }
}
