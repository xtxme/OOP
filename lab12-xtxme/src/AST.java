import java.util.Map;

public class AST {
    interface Node {
        void prettyPrint(StringBuilder s);
    }
    interface Expr extends Node {
        int eval(Map<String, Integer> bindings) throws EvalError;
    }
    record IntLit(int val) implements Expr {
        public int eval(
                Map<String, Integer> bindings) {
            return val; //คืนค่าตัวเลขที่เก็บอยู่โดยตรง
        }
        public void prettyPrint(
                StringBuilder s) {
            s.append(val); //เพิ่มค่าของตัวเลขลงใน StringBuilder
        }
    }

    record Variable(String name)
            implements Expr {
        public int eval( //คืนค่าที่ผูกไว้กับตัวแปร หากไม่มีการผูกค่าจะโยน EvalError
                         Map<String, Integer> bindings) throws EvalError {
            if (bindings.containsKey(name))
                return bindings.get(name);
            throw new EvalError("undefined variable: " + name);
        }
        public void prettyPrint(StringBuilder s) {
            s.append(name);
        }
    }

    record BinaryArithExpr(Expr left, String op, Expr right) implements Expr {
        public int eval(Map<String, Integer> bindings) throws EvalError {
            int lv = left.eval(bindings);
            int rv = right.eval(bindings);
            if (op.equals("+")) return lv + rv;
            if (op.equals("*")) return lv * rv;
            if (op.equals("-")) return lv - rv;
            if (op.equals("/")) return lv / rv;
            if (op.equals("%")) return lv % rv;
            throw new EvalError("unknown op: " + op);
        }
        public void prettyPrint(StringBuilder s) {//แสดงในรูปแบบวงเล็บ
            s.append("(");// เริ่มต้นวงเล็บ
            left.prettyPrint(s);
            s.append(op);
            right.prettyPrint(s);

            s.append(")");
        }
    }

    static class EvalError extends Exception {//ใช้สำหรับแสดงข้อผิดพลาดในการประเมินค่า
        public EvalError(String message) {// Constructor รับข้อความข้อผิดพลาด
            super(message);// ส่งข้อความไปยังคลาสException
        }
    }

}
