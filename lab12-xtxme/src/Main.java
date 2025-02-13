import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String[] testExpressions = {
                "2+x*9",
                "261200",
                "2*x+6",
                "2+x*6",
        };

        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("x", 13);

        for (String expression : testExpressions) {
            System.out.println("Testing Expression: " + expression);
            ExprTokenizer tokenizer = new ExprTokenizer(expression);
            ExprParser parser = new ExprParser(tokenizer);

            StringBuilder sb = new StringBuilder();

            try {
                AST.Expr expr = parser.parse();

                expr.prettyPrint(sb);
                System.out.println("Pretty Print: " + sb);

                int result = expr.eval(bindings);
                System.out.println("Result: " + result);
            } catch (SyntaxError e) {
                System.err.println("Syntax Error: " + e.getMessage());
            } catch (AST.EvalError e) {
                System.err.println("Evaluation Error: " + e.getMessage());
            }

            System.out.println();
        }
    }
}
