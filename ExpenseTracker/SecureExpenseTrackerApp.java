import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SecureExpenseTrackerApp extends Application {
    private ExpenseTracker expenseTracker = new ExpenseTracker(1000);
    private Label totalSpendingLabel;
    private Label budgetLimitLabel;
    private Label budgetStatusLabel;

    // Simple in-memory user database
    private static final String CORRECT_USERNAME = "user";
    private static final String CORRECT_PASSWORD = "password";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Secure Expense Tracker");

        // Login screen components
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        // Event handler for login button
        loginButton.setOnAction(event -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            if (authenticateUser(enteredUsername, enteredPassword)) {
                showExpenseTracker(primaryStage);
            } else {
                showErrorAlert("Invalid username or password. Please try again.");
                usernameField.clear();
                passwordField.clear();
            }
        });

        // Layout for login screen
        GridPane loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(10, 10, 10, 10));

        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);
        loginGrid.add(loginButton, 0, 2);

        Scene loginScene = new Scene(loginGrid, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showExpenseTracker(Stage primaryStage) {
        // Launch the ExpenseTrackerApp scene
        ExpenseTrackerApp expenseTrackerApp = new ExpenseTrackerApp();
        expenseTrackerApp.start(primaryStage);
    }

    private boolean authenticateUser(String enteredUsername, String enteredPassword) {
        // Simulate a secure password check (insecure for demonstration purposes)
        return enteredUsername.equals(CORRECT_USERNAME) && enteredPassword.equals(CORRECT_PASSWORD);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
