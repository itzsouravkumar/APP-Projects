package main.bankingSystem.accounts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixedDepositAccount extends BankAccount {
    private static final double MINIMUM_BALANCE = 10000.0;
    private final int tenureMonths;
    private final LocalDateTime maturityDate;
    private boolean isMatured;

    public FixedDepositAccount(String accountNumber, String accountHolder,
            double depositAmount, int tenureMonths) {
        super(accountNumber, accountHolder, depositAmount);
        if (depositAmount < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Minimum FD amount is ₹" + MINIMUM_BALANCE);
        }
        this.tenureMonths = tenureMonths;
        this.maturityDate = LocalDateTime.now().plusMonths(tenureMonths);
        this.isMatured = false;
    }

    @Override
    public String getAccountType() {
        return "Fixed Deposit Account";
    }

    @Override
    public double getInterestRate() {
        if (tenureMonths <= 6)
            return 6.0;
        else if (tenureMonths <= 12)
            return 6.5;
        else if (tenureMonths <= 24)
            return 7.0;
        else
            return 7.5;
    }

    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    @Override
    public synchronized boolean withdraw(double amount) {
        if (!isMatured && LocalDateTime.now().isBefore(maturityDate)) {
            System.out.println("❌ Premature withdrawal not allowed! Maturity date: " +
                    maturityDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            return false;
        }
        return super.withdraw(amount);
    }

    public void checkMaturity() {
        if (!isMatured && LocalDateTime.now().isAfter(maturityDate)) {
            isMatured = true;
            calculateInterest();
            System.out.println("✅ FD has matured!");
        }
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public boolean isMatured() {
        return isMatured;
    }
}
