package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.BasicParser;
import prj.clark.lang.basic.env.DecimalData;
import prj.clark.lang.basic.env.IntegerData;
import prj.clark.lang.basic.env.StringData;

import java.util.HashMap;
import java.util.function.Supplier;

public class NodeFactory {
    private static final HashMap<String, Supplier<BinaryOperation>> OPERATION_MAP =
            new HashMap<String, Supplier<BinaryOperation>>() {{
                put("+", AdditionNode::new);
                put("-", SubtractionNode::new);
                put("*", ModulusNode::new);
                put("/", DivisionNode::new);
                put("^", ExponentNode::new);
                put("%", ModulusNode::new);
            }};

    private NodeFactory() {
    }

    public static ExpressionNode getExpression(BasicParser.ExpressionContext ctx) {
        // Handle the case of terminal nodes.
        if (ctx.DECIMAL() != null) {
            return new LiteralNode(new DecimalData(ctx.DECIMAL().getText()));
        } else if (ctx.INTEGER() != null) {
            return new LiteralNode(new IntegerData(ctx.INTEGER().getText()));
        } else if (ctx.STRING() != null) {
            return new LiteralNode(new StringData(ctx.STRING().getText()));
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
        if (ctx.assignment() != null) {
            BasicParser.AssignmentContext assignment = ctx.assignment();
            String identifier = assignment.IDENTIFIER().getText();

            AssignmentNode an = new AssignmentNode(identifier, getExpression(assignment.expression()));

            // Check if its an initial declaration.
            if (ctx.declaration() != null) {
                return new DeclarationNode(identifier, an);
            }

            return an;
        }

        // Otherwise, it's a print statement.
        return new PrintNode(getExpression(ctx.printStatement().expression()));
    }
}
