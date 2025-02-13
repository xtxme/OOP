import java.io.*;
import java.util.*;

public class readWrite {
    public static void main(String[] args) {
        String inFile = "input.txt";
        String outFile = "output.txt";

        testCase(inFile);

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))
        ) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                try {
                    int result = calculation(line);
                    writer.write(line + " = " + result);
                    writer.newLine();
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid in line: " + line);
                } catch (ArithmeticException e) {
                    System.out.println("Math error in line: " + line);
                }
            }

            System.out.println("Output has been written to: " + outFile);

        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // เมธอดที่เอาไว้คำนวณตัวเลข
    public static int calculation(String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Null or Empty expression");
        }

        String[] tokens = expression.split(" ");
        List<String> operators = Arrays.asList("+", "-", "*", "/", "%");

        Stack<Integer> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                values.push(Integer.parseInt(token));
            } else if (operators.contains(token)) {
                if (token.equals("+") && tokens.length > 1 && tokens[1].equals("-")) {
                    throw new IllegalArgumentException("Invalid operator: +");
                }

                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    if (values.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression: not enough operands");
                    }
                    int b = values.pop();
                    int a = values.pop();
                    values.push(applyOp(ops.pop(), b, a));
                }
                ops.push(token);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        // ทำการคำนวณที่เหลือใน stack
        while (!ops.isEmpty()) {
            if (values.size() < 2) {
                throw new IllegalArgumentException("Invalid expression: not enough operands for operation");
            }
            int b = values.pop();
            int a = values.pop();
            values.push(applyOp(ops.pop(), b, a));
        }

        return values.pop();
    }

    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ฟังก์ชันในการกำหนดลำดับความสำคัญของตัวดำเนินการ
    private static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-": return 1;
            case "*":
            case "/":
            case "%": return 2;
            default: return -1;
        }
    }

    private static int applyOp(String operator, int b, int a) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case "%":
                if (b == 0) throw new ArithmeticException("Modulo by zero");
                return a % b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public static void testCase(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // ตัวอย่างที่ถูกต้อง
            writer.write("99 + 26 - 1");
            writer.newLine();
            writer.write("100 * 7 / 2");
            writer.newLine();
            writer.write("100 % 30 + 20");
            writer.newLine();
            writer.write("23 + 45 * 6");
            writer.newLine();
            writer.write("89 / 2");
            writer.newLine();
            writer.write("89 % 2 % 3");
            writer.newLine();

            // ตัวอย่างที่ไม่ถูกต้อง
            writer.write("2 3 +");
            writer.newLine();
            writer.write("10 / 0");
            writer.newLine();
            writer.write("5 + ");
            writer.newLine();
            writer.write("5 % 0 ");
            writer.newLine();
            writer.write("5 / 0 % 0 ");
            writer.newLine();
            writer.write("Hello");
            writer.newLine();
            writer.write("5 + 10.8");
            writer.newLine();
            writer.write("5 + - 10");
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error creating input file: " + e.getMessage());
        }
    }
}