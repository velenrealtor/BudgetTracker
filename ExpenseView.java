package view;

import com.budgettracker.model.Expense;
import com.budgettracker.controller.ExpenseController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ExpenseView extends Application {

    private ExpenseController expenseController;

    private TableView<Expense> expenseTable;
    private TextField categoryField;
    private TextField amountField;
    private TextField descriptionField;
    private TextField dateField;

    public ExpenseView() {
        expenseController = new ExpenseController();
    }

    @Override
    public void start(Stage stage) {

        // Create UI elements
        expenseTable = new TableView<>();
        categoryField = new TextField();
        amountField = new TextField();
        descriptionField = new TextField();
        dateField = new TextField();

        // Table columns
        TableColumn<Expense, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Expense, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        expenseTable.getColumns().addAll(categoryColumn, amountColumn, descriptionColumn, dateColumn);

        // Buttons
        Button addButton = new Button("Add Expense");
        addButton.setOnAction(e -> addExpense());

        Button loadButton = new Button("Load Expenses");
        loadButton.setOnAction(e -> loadExpenses());

        // Layout
        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(
                new Label("Category"), categoryField,
                new Label("Amount"), amountField,
                new Label("Description"), descriptionField,
                new Label("Date"), dateField,
                addButton, loadButton
        );

        HBox layout = new HBox(10);
        layout.getChildren().addAll(formLayout, expenseTable);

        // Scene
        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Expense Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Method to add a new expense
    private void addExpense() {
        String category = categoryField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String date = dateField.getText();

        Expense expense = new Expense(category, amount, description, date);
        expenseController.addExpense(expense);
        clearFormFields();
    }

    // Method to load all expenses
    private void loadExpenses() {
        List<Expense> expenses = expenseController.getAllExpenses();
        expenseTable.getItems().setAll(expenses);
    }

    // Method to clear form fields
    private void clearFormFields() {
        categoryField.clear();
        amountField.clear();
        descriptionField.clear();
        dateField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
