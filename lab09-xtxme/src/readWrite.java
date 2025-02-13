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
                    int result = calculaion(line);
                    writer.write(line + " = " + result);
                    writer.newLine();
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid in line: " + line);
                } catch (ArithmeticException e) {
                    System.out.println("Math error in line: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // เมธอดที่เอาไว้คำนวณตัวเลข
    private static int calculaion(String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Null or Empty expression");
        }


        String[] tokens = expression.split(" ");
        List<String> operators = Arrays.asList("+", "-", "*", "/", "%");

        int result;
        try {
            result = Integer.parseInt(tokens[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid token: " + tokens[0]);
        }

        for (int i = 1; i < tokens.length; i += 2) {
            if (i + 1 >= tokens.length) {
                throw new IllegalArgumentException("Incomplete expression");
            }

            String operator = tokens[i]; // ตัวดำเนินการ
            if (!operators.contains(operator)) {
                throw new IllegalArgumentException("Invalid operator: " + operator);
            }

            int operand;
            try {
                operand = Integer.parseInt(tokens[i + 1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid token: " + tokens[i + 1]);
            }

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand == 0) throw new ArithmeticException("Division by zero");
                    result /= operand;
                    break;
                case "%":
                    if (operand == 0) throw new ArithmeticException("Modulo by zero");
                    result %= operand;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported this " + operator);
            }
        }

        return result;
    }

    // สร้างไฟล์อินพุตที่มีตัวอย่างการทดสอบ
    private static void testCase(String fileName) {
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
