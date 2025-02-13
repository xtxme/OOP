import java.util.NoSuchElementException;

interface Tokenizer {
    boolean hasNextToken();
    String peek();
    String consume();
}

interface Parser {
    int parse() throws SyntaxError;
}

class SyntaxError extends Exception {
    public SyntaxError(String message) {
        super(message);
    }
}

class ExprTokenizer implements Tokenizer {
    private String src, next;
    private int pos;

    public ExprTokenizer(String src) {
        this.src = src;
        pos = 0;
        computeNext();
    }

    public ExprTokenizer() {

    }

    public boolean hasNextToken() {
        return next != null;
    }

    public void checkNextToken()  {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
    }

    public String peek() {
        checkNextToken();
        return next;
    }

    public String consume() {
        checkNextToken();
        String result = next;
        computeNext();
        return result;
    }

    private void computeNext() {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && Character.isSpaceChar(src.charAt(pos))) pos++; // Ignore whitespace
        if (pos == src.length()) {
            next = null;
            return; // No more tokens
        }

        char c = src.charAt(pos);
        if (Character.isDigit(c)) { // Start of number
            s.append(c);
            for (pos++; pos < src.length() && Character.isDigit(src.charAt(pos)); pos++) {
                s.append(src.charAt(pos));
            }
        } else if (c == '+' || c == '(' || c == '-' || c == '*' || c == '/' || c == ')' || c == '%') {
            s.append(c);
            pos++;
        } else {
            throw new NoSuchElementException("unknown character: " + c);
        }
        next = s.toString();
    }

    public void consume(String expected) {
        if (peek().equals(expected)) {
            consume();
        } else {
            throw new NoSuchElementException(expected + " expected");
        }
    }

    class ExprParser implements Parser {
        private Tokenizer tkz;

        public ExprParser(Tokenizer tkz) {
            this.tkz = tkz;
        }

        public boolean peek(String s) {
            if (!tkz.hasNextToken())
                return false;
            return tkz.peek().equals(s);
        }

        public void consume(String s) throws SyntaxError {
            if (peek(s))
                tkz.consume();
            else
                throw new SyntaxError(s + " expected");
        }

        public int parse() throws SyntaxError {
            int result = parseE();
            if (tkz.hasNextToken()) {
                throw new SyntaxError("Leftover token: " + tkz.peek());
            }
            return result;
        }

        //E → E + T | E - T | T
        private int parseE() throws SyntaxError {
            int value = parseT();
            while (tkz.hasNextToken() && (peek("+") || peek("-"))) {
                if (peek("+")) {
                    consume("+");
                    value += parseT();
                } else if (peek("-")) {
                    consume("-");
                    value -= parseT();
                }
            }
            return value;
        }

        //T → T * F | T / F | T % F | F
        private int parseT() throws SyntaxError {
            int value = parseF();
            while (tkz.hasNextToken() && (peek("*") || peek("/") || peek("%"))) {
                if (peek("*")) {
                    consume("*");
                    value *= parseF();
                } else if (peek("/")) {
                    consume("/");
                    int divisor = parseF();
                    if (divisor == 0) {
                        throw new SyntaxError("Division by zero");
                    }
                    value /= divisor;
                } else if (peek("%")) {
                    consume("%");
                    int divisor = parseF();
                    if (divisor == 0) {
                        throw new SyntaxError("Modulo by zero");
                    }
                    value %= divisor;
                }
            }
            return value;
        }

        // F → n | ( E )
        private int parseF() throws SyntaxError {
            if (isNumber(tkz.peek())) {
                String tok = tkz.consume();
                return Integer.parseInt(tok);
            } else {
                if (peek("(")) {
                    consume("(");
                }
                int value = parseE();
                if (peek(")")) {
                    consume(")");
                }
                return value;
            }
        }

        private boolean isNumber(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Test cases to evaluate
        String[] expressions = {
                "99 + 26 - 1",
                "100 * 7 / 2",
                "89 % 2 % 3",
                "89 / 2",
                "2 3 +",
                "10 / 0",
                "Hello",
                "5 + - 10"
        };

        // Iterate through each expression and parse
        for (String expression : expressions) {
            System.out.println("Evaluating: " + expression);
            ExprTokenizer tokenizer = new ExprTokenizer(expression);
            ExprTokenizer.ExprParser parser = new ExprTokenizer().new ExprParser(tokenizer);

            try {
                // Parse the expression and print the result
                int result = parser.parse();
                System.out.println("Result: " + result);
            } catch (SyntaxError e) {
                System.err.println("Syntax error: " + e.getMessage());
            }
            System.out.println();
        }

    }
}

