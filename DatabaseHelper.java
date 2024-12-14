package model;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:budget_tracker.db";
    private Connection connection;

    public DatabaseHelper() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String createExpensesTable = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "category TEXT, " +
                    "amount REAL, " +
                    "description TEXT, " +
                    "date TEXT)";
            stmt.execute(createExpensesTable);

            String createBudgetTable = "CREATE TABLE IF NOT EXISTS budget (" +
                    "id INTEGER PRIMARY KEY, " +
                    "total_budget REAL, " +
                    "remaining_balance REAL)";
            stmt.execute(createBudgetTable);

            String checkBudget = "SELECT COUNT(*) FROM budget";
            ResultSet rs = stmt.executeQuery(checkBudget);
            if (rs.getInt(1) == 0) {
                String insertDefaultBudget = "INSERT INTO budget (id, total_budget, remaining_balance) VALUES (1, 1000, 1000)";
                stmt.execute(insertDefaultBudget);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExpense(Expense expense) {
        String query = "INSERT INTO expenses (category, amount, description, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, expense.getCategory());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getDescription());
            pstmt.setString(4, expense.getDate());
            pstmt.executeUpdate();
            updateRemainingBudget(expense.getAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRemainingBudget(double expenseAmount) {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT remaining_balance FROM budget WHERE id = 1";
            ResultSet rs = stmt.executeQuery(query);
            double remainingBalance = rs.getDouble("remaining_balance");
            remainingBalance -= expenseAmount;
            String updateBudget = "UPDATE budget SET remaining_balance = ? WHERE id = 1";
            try (PreparedStatement pstmt = connection.prepareStatement(updateBudget)) {
                pstmt.setDouble(1, remainingBalance);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Budget getBudget() {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM budget WHERE id = 1";
            ResultSet rs = stmt.executeQuery(query);
            return new Budget(rs.getDouble("total_budget"), rs.getDouble("remaining_balance"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getExpenses() {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery("SELECT * FROM expenses");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
