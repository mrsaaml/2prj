import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("Please enter your arithmetic expression(or type 'history' to view past calculations): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }else if (input.equalsIgnoreCase("history")) {
                showHistory();
                continue;
            }

            try {
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
                history.add(input + " = " + result);
            } catch (Exception e) {
                System.out.println("Invalid expression. Please try again.");
            }

            System.out.print("Do you want to continue? (y/n): ");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("n")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }
        }
        scanner.close();
    }
    private static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            System.out.println("Calculation History:");
            for (String record : history) {
                System.out.println(record);
            }
        }
    }

    public static double evaluateExpression(String expression) {
        return new ExpressionParser().parse(expression);
    }
}

class ExpressionParser {
    private int pos = -1, ch;
    private String input;

    public double parse(String str) {
        input = str;
        pos = -1;
        nextChar();
        double x = parseExpression();
        if (pos < input.length()) throw new RuntimeException("Unexpected character: " + (char) ch);
        return x;
    }

    private void nextChar() {
        ch = (++pos < input.length()) ? input.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (ch == ' ') nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    private double parseExpression() {
        double x = parseTerm();
        while (true) {
            if (eat('+')) x += parseTerm();
            else if (eat('-')) x -= parseTerm();
            else return x;
        }
    }

    private double parseTerm() {
        double x = parseFactor();
        while (true) {
            if (eat('*')) x *= parseFactor();
            else if (eat('/')) {
                double divisor = parseFactor();
                if (divisor == 0) throw new ArithmeticException("Division by zero");
                x /= divisor;
            } else if (eat('%')) { // Modulus operator
                double divisor = parseFactor();
                if (divisor == 0) throw new ArithmeticException("Division by zero");
                x %= divisor;  // Perform modulus operation
            } else return x;
        }
    }

    private double parseFactor() {
        if (eat('+')) return parseFactor();
        if (eat('-')) return -parseFactor();

        double x;
        int startPos = this.pos;
        if (eat('(')) {
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') {
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            x = Double.parseDouble(input.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') {
            while (ch >= 'a' && ch <= 'z') nextChar();
            String func = input.substring(startPos, this.pos);
            switch (func) {
                case "sqrt" -> x = Math.sqrt(parseFactor());
                case "abs" -> x = Math.abs(parseFactor());
                case "round" -> x = Math.round(parseFactor());
                case "power" -> {
                    if (!eat('(')) throw new RuntimeException("Missing opening parenthesis for power");
                    double base = parseExpression();
                    if (!eat(',')) throw new RuntimeException("Missing comma between base and exponent");
                    double exponent = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing closing parenthesis for power");
                    x = Math.pow(base, exponent);
                }
                default -> throw new RuntimeException("Unknown function: " + func);
            }
        } else {
            throw new RuntimeException("Unexpected character: " + (char) ch);
        }
        return x;
    }
}