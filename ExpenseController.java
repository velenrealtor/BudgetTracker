package controller;

import com.budgettracker.model.DatabaseHelper;
import com.budgettracker.model.Expense;

public class ExpenseController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    public void addExpense(String category, String amount, String description) {
        double expenseAmount = Double.parseDouble(amount);
        Expense expense = new Expense(category, expenseAmount, description, "2024-12-14");
        databaseHelper.addExpense(expense);
    }
}
