import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker {
    private double budgetLimit;
    private List<Expense> expenses;

    public ExpenseTracker(double budgetLimit) {
        this.budgetLimit = budgetLimit;
        this.expenses = new ArrayList<>();
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getTotalSpending() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public boolean checkBudgetStatus() {
        return getTotalSpending() > budgetLimit;
    }

    public void clearExpenses() {
        expenses.clear();
    }
}
