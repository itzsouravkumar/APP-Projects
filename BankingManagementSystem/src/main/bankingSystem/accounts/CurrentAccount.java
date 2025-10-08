package main.bankingSystem.accounts;

public class CurrentAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.0;
    private static final double MINIMUM_BALANCE = 5000.0;
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder,
                          double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolder, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String getAccountType() { return "Current Account"; }

    @Override
    public double getInterestRate() { return INTEREST_RATE; }

    @Override
    public double getMinimumBalance() { return -overdraftLimit; }

    public double getOverdraftLimit() { return overdraftLimit; }

    public void setOverdraftLimit(double limit) {
        this.overdraftLimit = limit;
        System.out.println("✅ Overdraft limit updated to ₹" +
                String.format("%.2f", limit));
    }
}
