package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.BasicParser;
import prj.clark.lang.basic.env.DecimalData;
import prj.clark.lang.basic.env.IntegerData;
import prj.clark.lang.basic.env.StringData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NodeFactory {
    private static final Map<String, Supplier<BinaryOperation>> OPERATION_MAP;

    static {
        HashMap<String, Supplier<BinaryOperation>> operations = new HashMap<>();
        operations.put("+", AdditionNode::new);
        operations.put("-", SubtractionNode::new);
        operations.put("*", ModulusNode::new);
        operations.put("/", DivisionNode::new);
        operations.put("^", ExponentNode::new);
        operations.put("%", ModulusNode::new);
        OPERATION_MAP = Collections.unmodifiableMap(operations);
    }

    private NodeFactory() {}

    public static ExpressionNode getExpression(BasicParser.ExpressionContext ctx) {
        // Handle the case of terminal nodes.
        if (ctx.DECIMAL() != null) {
            return new LiteralNode(new DecimalData(ctx.DECIMAL().getText()));
        } else if (ctx.INTEGER() != null) {
            return new LiteralNode(new IntegerData(ctx.INTEGER().getText()));
        } else if (ctx.STRING() != null) {
            // Strip the leading and trailing quotes that remain from the parsing stage.
            String string = ctx.STRING().getText();
            string = string.substring(1, string.length() - 1);
            return new LiteralNode(new StringData(string));
        } else if (ctx.IDENTIFIER() != null) {
            return new IdentifierNode(ctx.IDENTIFIER().getText());
        }

        // Handle the instance of casting.
        if (ctx.AS() != null) {
            return new CastNode(getExpression(ctx.value), ctx.target.getText());
        }

        // In any other case, we're dealing with a binary operator.
        BinaryOperation op = OPERATION_MAP.get(ctx.operator.getText()).get();

        op.setLeft(getExpression(ctx.left));
        op.setRight(getExpression(ctx.right));

        return op;
    }

    public static Node getStatement(BasicParser.StatementContext ctx) {
        if (ctx.printStatement() != null) {
            return new PrintNode(getExpression(ctx.printStatement().expression()));
        }

        if (ctx.declaration() != null) {
            BasicParser.AssignmentContext assignment = ctx.declaration().assignment();
            String identifier = assignment.IDENTIFIER().getText();

            return new DeclarationNode(identifier, getExpression(assignment.expression()));
        }

        if (ctx.assignment() != null) {
            BasicParser.AssignmentContext assignment = ctx.assignment();
            String identifier = assignment.IDENTIFIER().getText();

            return new AssignmentNode(identifier, getExpression(assignment.expression()));
        }

        return getExpression(ctx.expression());
    }
}
