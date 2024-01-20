
package combinedprogramgui;

import javax.swing.*;
import java.awt.*;

public class CombinedProgramGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Combined Program");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 1));

            JButton fibonacciButton = new JButton("Fibonacci Series Generator");
            JButton factorizationButton = new JButton("Factorization");
            JButton palindromeButton = new JButton("Palindrome Checker");
            JButton armstrongButton = new JButton("Armstrong Number Checker");
            JButton factorialButton = new JButton("Factorial Calculator");
            JButton exitButton = new JButton("Exit");

            panel.add(fibonacciButton);
            panel.add(factorizationButton);
            panel.add(palindromeButton);
            panel.add(armstrongButton);
            panel.add(factorialButton);
            panel.add(exitButton);
            
            

            frame.getContentPane().add(BorderLayout.CENTER, panel);

            fibonacciButton.addActionListener(e -> {
                int n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of terms for Fibonacci series: "));
                String result = generateFibonacciSeries(n);
                showMessage("Fibonacci Series", result);
            });

            factorizationButton.addActionListener(e -> {
                int num = Integer.parseInt(JOptionPane.showInputDialog("Enter a number for factorization: "));
                String result = factorization(num);
                showMessage("Factorization", result);
            });

            palindromeButton.addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Enter a string for palindrome check: ");
                boolean isPalindrome = checkPalindrome(input);
                showMessage("Palindrome Check", "The string is " + (isPalindrome ? "a palindrome." : "not a palindrome."));
            });

            armstrongButton.addActionListener(e -> {
                int num = Integer.parseInt(JOptionPane.showInputDialog("Enter a number for Armstrong check: "));
                boolean isArmstrong = checkArmstrongNumber(num);
                showMessage("Armstrong Number Check", "The number is " + (isArmstrong ? "an Armstrong number." : "not an Armstrong number."));
            });

            factorialButton.addActionListener(e -> {
                int num = Integer.parseInt(JOptionPane.showInputDialog("Enter a non-negative integer for factorial calculation: "));
                long result = calculateFactorial(num);
                showMessage("Factorial", "Factorial of " + num + " is: " + result);
            });

            exitButton.addActionListener(e -> System.exit(0));

            frame.setVisible(true);
        });
    }

    private static String generateFibonacciSeries(int n) {
        // Replace this with your specific Fibonacci series generation logic
        StringBuilder result = new StringBuilder();
        int firstTerm = 0, secondTerm = 1;

        result.append("Fibonacci Series:\n");
        for (int i = 1; i <= n; ++i) {
            result.append(firstTerm).append(", ");
            int nextTerm = firstTerm + secondTerm;
            firstTerm = secondTerm;
            secondTerm = nextTerm;
        }
        return result.toString();
    }

    private static String factorization(int num) {
        StringBuilder result = new StringBuilder();
        result.append("Prime factors of ").append(num).append(" are: ");

        for (int i = 2; i <= num; i++) {
            while (num % i == 0) {
                result.append(i).append(" ");
                num = num / i;
            }
        }
        return result.toString();
    }

    private static boolean checkPalindrome(String input) {
        String reversed = new StringBuilder(input).reverse().toString();
        return input.equals(reversed);
    }

    private static boolean checkArmstrongNumber(int num) {
        int originalNum = num;
        int result = 0;
        int n = String.valueOf(num).length();

        while (num != 0) {
            int digit = num % 10;
            result += Math.pow(digit, n);
            num /= 10;
        }

        return result == originalNum;
    }

    private static long calculateFactorial(int num) {
        long factorial = 1;

        for (int i = 1; i <= num; ++i) {
            factorial *= i;
        }

        return factorial;
    }

    private static void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
