
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Expense {
    private final SimpleStringProperty description;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty category;
    private final SimpleObjectProperty<LocalDate> date;

    public Expense(String description, double amount, String category, LocalDate date) {
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.date = new SimpleObjectProperty<>(date);
    }

    public String getDescription() {
        return description.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public String getCategory() {
        return category.get();
    }

    public LocalDate getDate() {
        return date.get();
    }
}
