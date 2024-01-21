import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseTrackerApp extends Application {
    private ExpenseTracker expenseTracker;
    private Label totalSpendingLabel;
    private Label budgetLimitLabel;
    private Label budgetStatusLabel;

    private TableView<Expense> expenseTable;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        expenseTracker = new ExpenseTracker(1000); // Default budget limit

        primaryStage.setTitle("Expense Tracker");

        // GUI components
        Label descriptionLabel = new Label("Description:");
        Label amountLabel = new Label("Amount:");
        Label categoryLabel = new Label("Category:");
        Label dateLabel = new Label("Date:");

        TextField descriptionField = new TextField();
        TextField amountField = new TextField();
        TextField categoryField = new TextField();
        DatePicker datePicker = new DatePicker();

        Button addButton = new Button("Add Expense");
        totalSpendingLabel = new Label("Total Spending: $" + expenseTracker.getTotalSpending());
        budgetLimitLabel = new Label("Budget Limit: $" + expenseTracker.getBudgetLimit());
        budgetStatusLabel = new Label();

        // Configure columns for the table
        expenseTable = new TableView<>();
        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Expense, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Expense, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        expenseTable.getColumns().addAll(descriptionColumn, amountColumn, categoryColumn, dateColumn);

        // Event handlers
        addButton.setOnAction(event -> {
            try {
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                LocalDate date = datePicker.getValue();

                Expense newExpense = new Expense(description, amount, category, date);
                expenseTracker.addExpense(newExpense);

                updateTable();
                updateLabels();

                descriptionField.clear();
                amountField.clear();
                categoryField.clear();
                datePicker.setValue(null);
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid amount. Please enter a valid number.");
            }
        });

        // Clear Data button
        Button clearDataButton = new Button("Clear Data");
        clearDataButton.setOnAction(event -> {
            clearData();
            updateTable();
            updateLabels();
        });

        // Load Data button
        Button loadDataButton = new Button("Load Data");
        loadDataButton.setOnAction(event -> {
            loadDataFromFile();
            updateTable();
            updateLabels();
        });

        // Save Data button
        Button saveDataButton = new Button("Save Data");
        saveDataButton.setOnAction(event -> saveDataToFile());

        // Set Budget Limit button
        Button setBudgetLimitButton = new Button("Set Budget Limit");
        setBudgetLimitButton.setOnAction(event -> setBudgetLimit());

        // Layout
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10, 10, 10, 10));

        inputGrid.add(descriptionLabel, 0, 0);
        inputGrid.add(descriptionField, 1, 0);
        inputGrid.add(amountLabel, 0, 1);
        inputGrid.add(amountField, 1, 1);
        inputGrid.add(categoryLabel, 0, 2);
        inputGrid.add(categoryField, 1, 2);
        inputGrid.add(dateLabel, 0, 3);
        inputGrid.add(datePicker, 1, 3);
        inputGrid.add(addButton, 0, 4);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(clearDataButton, loadDataButton, saveDataButton, setBudgetLimitButton);

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                inputGrid, expenseTable, totalSpendingLabel, budgetLimitLabel, budgetStatusLabel, buttonBox
        );

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial data
        loadDataFromFile();
        updateTable();
        updateLabels();
    }

    private void updateTable() {
        expenseTable.getItems().setAll(expenseTracker.getExpenses());
    }

    private void updateLabels() {
        double totalSpending = expenseTracker.getTotalSpending();
        double budgetLimit = expenseTracker.getBudgetLimit();

        totalSpendingLabel.setText("Total Spending: $" + totalSpending);
        budgetLimitLabel.setText("Budget Limit: $" + budgetLimit);

        if (expenseTracker.checkBudgetStatus()) {
            budgetStatusLabel.setText("Warning: You have exceeded your budget limit!");
        } else {
            budgetStatusLabel.setText("You are within your budget limit.");
        }
    }

    private void clearData() {
        expenseTracker.clearExpenses();
        updateTable();
        updateLabels();
    }

    private void loadDataFromFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Expense Data File");
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                List<Expense> expenses = readExpensesFromFile(file);
                expenseTracker.setExpenses(expenses);
            }
        } catch (IOException e) {
            showErrorAlert("Error loading data from file.");
        }
    }

    private List<Expense> readExpensesFromFile(File file) throws IOException {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String description = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    LocalDate date = LocalDate.parse(parts[3]);
                    expenses.add(new Expense(description, amount, category, date));
                }
            }
        }
        return expenses;
    }

    private void saveDataToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Expense Data File");
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                writeExpensesToFile(file);
            }
        } catch (IOException e) {
            showErrorAlert("Error saving data to file.");
        }
    }

    private void writeExpensesToFile(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Expense expense : expenseTracker.getExpenses()) {
                writer.write(expense.getDescription() + "," +
                        expense.getAmount() + "," +
                        expense.getCategory() + "," +
                        expense.getDate() + "\n");
            }
        }
    }

    private void setBudgetLimit() {
        TextInputDialog dialog = new TextInputDialog(Double.toString(expenseTracker.getBudgetLimit()));
        dialog.setTitle("Set Budget Limit");
        dialog.setHeaderText("Enter the new budget limit:");
        dialog.setContentText("Budget Limit:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            try {
                double newBudgetLimit = Double.parseDouble(s);
                expenseTracker.setBudgetLimit(newBudgetLimit);
                updateLabels();
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
