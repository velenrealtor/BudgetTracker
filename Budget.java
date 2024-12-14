package model;

public class Budget {
    private double totalBudget;
    private double remainingBalance;

    public Budget(double totalBudget, double remainingBalance) {
        this.totalBudget = totalBudget;
        this.remainingBalance = remainingBalance;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
