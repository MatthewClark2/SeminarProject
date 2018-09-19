package prj.clark.lang.math;

import java.util.function.BiFunction;

public class BasicMathVisitor extends MathBaseVisitor<Double> {
    @Override
    public Double visitStatement(MathParser.StatementContext ctx) {
        // Do some sort of checking to ensure that the expression is correctly returned.
        return visitExpression(ctx.expression());
    }

    @Override
    public Double visitExpression(MathParser.ExpressionContext ctx) {
        if (ctx.LPAREN() != null) {
            // The contents of parenthesis are stored as solitary expressions.
            return visitExpression(ctx.expression(0));
        }

        if (ctx.NUMBER() != null) {
            // The expression is terminal.
            return Double.parseDouble(ctx.NUMBER().getText());
        }

        if (ctx.EXP() != null) {
            return apply(ctx, 0, 1, Math::pow);
        }

        if (ctx.MD() != null) {
            switch (ctx.MD().getText()) {
                case "*": return apply(ctx, 0, 1, (x, y) -> x * y);
                case "/": return apply(ctx, 0, 1, (x, y) -> x / y);
            }
        }

        if (ctx.AS() != null) {
            switch (ctx.AS().getText()) {
                case "+": return apply(ctx, 0, 1, (x, y) -> x + y);
                case "-": return apply(ctx, 0, 1, (x, y) -> x - y);
            }
        }

        throw new IllegalStateException("Something was not handled\n" + ctx.getText());
    }

    private Double apply(MathParser.ExpressionContext ctx, int pos1, int pos2, BiFunction<Double, Double, Double> fn) {
        double arg1 = visitExpression(ctx.expression(pos1));
        double arg2 = visitExpression(ctx.expression(pos2));
        return fn.apply(arg1, arg2);
    }
}
