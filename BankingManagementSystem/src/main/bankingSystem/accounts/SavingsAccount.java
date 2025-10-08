package main.bankingSystem.accounts;

public class SavingsAccount extends BankAccount {
    private static final double INTEREST_RATE = 4.5;
    private static final double MINIMUM_BALANCE = 1000.0;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, initialBalance);
    }

    @Override
    public String getAccountType() { return "Savings Account"; }

    @Override
    public double getInterestRate() { return INTEREST_RATE; }

    @Override
    public double getMinimumBalance() { return MINIMUM_BALANCE; }
}
