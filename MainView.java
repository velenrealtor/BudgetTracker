package view;

import com.budgettracker.controller.ExpenseController;
import com.budgettracker.controller.BudgetController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainView extends Application {

    private final BudgetController budgetController = new BudgetController();
    private final ExpenseController expenseController = new ExpenseController();

    @Override
    public void start(Stage primaryStage) {
        // Layout
        BorderPane root = new BorderPane();
        VBox topSection = new VBox(10);
        HBox expenseSection = new HBox(10);

        // Budget Display
        Label budgetLabel = new Label("Total Budget: $1000");
        Label remainingLabel = new Label("Remaining Budget: $1000");

        // Expense Form
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        Button addExpenseButton = new Button("Add Expense");

        addExpenseButton.setOnAction(e -> {
            expenseController.addExpense(categoryField.getText(), amountField.getText(), descriptionField.getText());
            // Update the remaining budget
            budgetController.updateBudget(remainingLabel);
        });

        expenseSection.getChildren().addAll(categoryField, amountField, descriptionField, addExpenseButton);
        topSection.getChildren().addAll(budgetLabel, remainingLabel, expenseSection);

        // Add everything to root
        root.setTop(topSection);

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Budget Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
