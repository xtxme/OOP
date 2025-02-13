interface Parser {
    AST.Expr parse() throws SyntaxError;
}

class SyntaxError extends Exception {
    public SyntaxError(String message) {
        super(message);
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

    public AST.Expr parse() throws SyntaxError {
        AST.Expr result = parseE();
        if (tkz.hasNextToken()) {
            throw new SyntaxError("Leftover token: " + tkz.peek());
        }
        return result;
    }

    //E → E + T | E - T | T
    private AST.Expr parseE() throws SyntaxError {
        AST.Expr value = parseT();
        while (tkz.hasNextToken() && (peek("+") || peek("-"))) {
            if (peek("+")) {
                consume("+");
                value = new AST.BinaryArithExpr(value, "+", parseT());
            } else if (peek("-")) {
                consume("-");
                value = new AST.BinaryArithExpr(value, "-", parseT());
            }
        }
        return value;
    }

    //T → T * F | T / F | T % F | F
    private AST.Expr parseT() throws SyntaxError {
        AST.Expr value = parseF();
        while (tkz.hasNextToken() && (peek("*") || peek("/") || peek("%"))) {
            if (peek("*")) {
                consume("*");
                value = new AST.BinaryArithExpr(value, "*", parseF());
            } else if (peek("/")) {
                consume("/");
                value = new AST.BinaryArithExpr(value, "/", parseF());
            } else if (peek("%")) {
                consume("%");
                value = new AST.BinaryArithExpr(value, "%", parseF());
            }
        }
        return value;
    }

    // F → n | x | ( E )
    private AST.Expr parseF() throws SyntaxError {
        if (isNumber(tkz.peek())) {
            String tok = tkz.consume(); // อ่าน token ปัจจุบันและชี้ไปยัง token ถัดไป
            return new AST.IntLit(Integer.parseInt(tok));// สร้างโหนด AST สำหรับค่าคงที่
        } else if (isVariable(tkz.peek())) {
            String tok = tkz.consume();
            return new AST.Variable(tok);
        } else {
            consume("(");
            AST.Expr value = parseE();// เรียก parseE()
            consume(")");
            return value;
        }
    }

    private boolean isNumber(String s) {
        try {
            Integer.parseInt(s); //แปลง string เ็นจำนวนเต็มเ
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isVariable(String s) {
        return s.matches("[a-zA-Z]+");
    }
}