package controller;

import com.budgettracker.model.DatabaseHelper;
import com.budgettracker.model.Budget;
import javafx.scene.control.Label;

public class BudgetController {

    private final DatabaseHelper databaseHelper = new DatabaseHelper();

    public void updateBudget(Label remainingLabel) {
        Budget budget = databaseHelper.getBudget();
        remainingLabel.setText("Remaining Budget: " + budget.getRemainingBalance());
    }
}
