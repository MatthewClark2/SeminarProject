package prj.clark.lang.math;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class MathApp {
    public static void main(String[] args) {
        System.out.println(evaluate("5 + (3 / 2) ^ 3"));
    }

    public static double evaluate(String expr) {
        if (! expr.endsWith("\n")) {
            expr += "\n";
        }

        ANTLRInputStream is = new ANTLRInputStream(expr);
        MathLexer lexer = new MathLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        MathParser parser = new MathParser(cts);
        parser.setBuildParseTree(true);
        MathParser.StatementContext ctx = parser.statement();
        BasicMathVisitor visitor = new BasicMathVisitor();

        return visitor.visitStatement(ctx);
    }
}
