package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.LangParser;
import prj.clark.lang.impl.env.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * This is basically just a sample class for converting the expression nodes of a parse tree into chunks of an AST.
 * It does not cover a solid chunk of the actual language.
 */
public class ExpressionFactory {
    private static final Map<String, BiFunction<Node, Node, Node>> BINOP_SUPPLIER = new HashMap<>();

    static {
        BINOP_SUPPLIER.put("^", ExponentialNode::new);
        BINOP_SUPPLIER.put("*", MultiplicationNode::new);
        BINOP_SUPPLIER.put("/", DivisionNode::new);
        BINOP_SUPPLIER.put("%", ModulusNode::new);
        BINOP_SUPPLIER.put("+", AdditionNode::new);
        BINOP_SUPPLIER.put("-", SubtractionNode::new);
        BINOP_SUPPLIER.put("<", LessThanNode::new);
        BINOP_SUPPLIER.put(">", GreaterThanNode::new);
        BINOP_SUPPLIER.put("<=", LessThanEqualNode::new);
        BINOP_SUPPLIER.put(">=", GreaterThanEqualNode::new);
        BINOP_SUPPLIER.put("==", EqualNode::new);
        BINOP_SUPPLIER.put("!=", NotEqualNode::new);
    }

    public Node getExpression(LangParser.ExpressionContext ctx) {
        // Handle parentheses.
        if (ctx.LPAREN() != null) {
            return getExpression(ctx.expression(0));
        }

        // Handle binary operators.
        if (ctx.op != null) {
            return BINOP_SUPPLIER.get(ctx.op.getText()).apply(getExpression(ctx.left), getExpression(ctx.right));
        }

        // Handle terminals.
        if (ctx.primitive() != null) {
            Data data;

            LangParser.PrimitiveContext pctx = ctx.primitive();

            if (pctx.bool() != null) {
                data = LangBool.of(pctx.bool().getText());
            } else if (pctx.FLOAT() != null) {
                data = LangFloat.of(pctx.FLOAT().getText());
            } else if (pctx.INT() != null) {
                data = LangInt.of(pctx.INT().getText());
            } else if (pctx.STRING() != null) {
                data = LangString.of(pctx.STRING().getText());
            } else {
                throw new IllegalStateException("Illegal primitive.");
            }

            return new LiteralNode(data);
        }

        // Logical inversion.
        if (ctx.NOT() != null) {
            return new LogicalInversionNode(getExpression(ctx.expression(0)));
        }

        // Handle conditions. Currently ignores elif.
        if (ctx.conditional() != null) {
            LangParser.ConditionalContext cctx = ctx.conditional();

            if (cctx.ELIF() != null) {
                throw new UnsupportedOperationException();
            }

            return new Conditional(getStatmentBody(cctx.statementBody(0)),
                    getStatmentBody(cctx.statementBody(1)),
                    getExpression(cctx.expression(0)));
        }

        // TODO(matthew-c21) - Add creation of identifier and binding nodes.
        // Basic binding support.


        // Otherwise, we're dealing with a lambda, function call, or collection. These aren't supported quite yet.
        throw new UnsupportedOperationException();
    }

    public Node getStatmentBody(LangParser.StatementBodyContext ctx) {
        List<Node> statements = new ArrayList<>();

        for (LangParser.StatementContext statement : ctx.statement()) {
            statements.add(getExpression(statement.expression()));
        }

        return new StatementListNode(statements);
    }
}
