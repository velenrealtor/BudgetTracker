package view;

import com.budgettracker.model.Budget;
import com.budgettracker.controller.BudgetController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class BudgetView extends Application {

    private BudgetController budgetController;

    private TableView<Budget> budgetTable;
    private TextField categoryField;
    private TextField amountField;
    private TextField descriptionField;
    private TextField monthField;

    public BudgetView() {
        budgetController = new BudgetController();
    }

    @Override
    public void start(Stage stage) {

        // Create UI elements
        budgetTable = new TableView<>();
        categoryField = new TextField();
        amountField = new TextField();
        descriptionField = new TextField();
        monthField = new TextField();

        // Table columns
        TableColumn<Budget, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        TableColumn<Budget, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        TableColumn<Budget, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Budget, String> monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());

        budgetTable.getColumns().addAll(categoryColumn, amountColumn, descriptionColumn, monthColumn);

        // Buttons
        Button addButton = new Button("Add Budget");
        addButton.setOnAction(e -> addBudget());

        Button loadButton = new Button("Load Budgets");
        loadButton.setOnAction(e -> loadBudgets());

        // Layout
        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(
                new Label("Category"), categoryField,
                new Label("Amount"), amountField,
                new Label("Description"), descriptionField,
                new Label("Month"), monthField,
                addButton, loadButton
        );

        HBox layout = new HBox(10);
        layout.getChildren().addAll(formLayout, budgetTable);

        // Scene
        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Budget Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Method to add a new budget
    private void addBudget() {
        String category = categoryField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String description = descriptionField.getText();
        String month = monthField.getText();

        Budget budget = new Budget(category, amount, description, month);
        budgetController.addBudget(budget);
        clearFormFields();
    }

    // Method to load all budgets
    private void loadBudgets() {
        List<Budget> budgets = budgetController.getAllBudgets();
        budgetTable.getItems().setAll(budgets);
    }

    // Method to clear form fields
    private void clearFormFields() {
        categoryField.clear();
        amountField.clear();
        descriptionField.clear();
        monthField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
