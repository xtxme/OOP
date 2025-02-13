import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String fileName = "runThis.txt"; // File containing expressions

        //สร้าง HashMap เพื่อใช้แทนค่าตัวแปร
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 13);

        // Create sample expressions file if it doesn't exist
        createSampleFile(fileName);

        // Run the parser 1,000,000 times
        for (int i = 0; i < 1_000_000; i++) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String expression;
                while ((expression = reader.readLine()) != null) {
                    processExpression(expression, bindings);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }
    }

    private static void processExpression(String expression, Map<String, Integer> bindings) {
        ExprTokenizer tokenizer = new ExprTokenizer(expression);
        ExprParser parser = new ExprParser(tokenizer);

        StringBuilder sb = new StringBuilder();

        try {
            AST.Expr expr = parser.parse();

            expr.prettyPrint(sb);
            System.out.print("Pretty Print: " + sb); //แปลง AST เป็น Pretty Print

            int result = expr.eval(bindings);
            System.out.print(" Result: " + result);
        } catch (SyntaxError e) {
            System.err.println("Syntax Error: " + e.getMessage());
        } catch (AST.EvalError e) {
            System.err.println("Evaluation Error: " + e.getMessage());
        }

        System.out.println();
    }

    private static void createSampleFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("2 + x * 9\n");
                writer.write("261200 \n  ");
                writer.write("2 * x + 6\n");
                writer.write("2 + x * 6\n");
            } catch (IOException e) {
                System.err.println("Error creating sample file: " + e.getMessage());
            }
        }
    }
}
