import java.util.NoSuchElementException;

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
        } else if (Character.isLetter(c)) { // Start of variable (a-z, A-Z)
            s.append(c);
            pos++; // Move to the next character
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
}

