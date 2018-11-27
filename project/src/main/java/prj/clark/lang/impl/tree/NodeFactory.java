package prj.clark.lang.impl.tree;

import org.antlr.v4.runtime.tree.ParseTree;
import prj.clark.lang.impl.LangParser;
import prj.clark.lang.impl.env.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * This is basically just a sample class for converting the expression nodes of a parse tree into chunks of an AST.
 * It does not cover a solid chunk of the actual language.
 */
public class NodeFactory {
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

    public List<Node> getAll(LangParser.FileContext ctx) {
        return ctx.statement().stream().filter(x -> x.comment() != null).map(this::get).collect(Collectors.toList());
    }

    public Node get(LangParser.StatementContext ctx) {
        // Check assignment first since an assignment contains an expression.
        if (ctx.assignment() != null) {
            return get(ctx.assignment());
        } else if (ctx.expression() != null) {
            return get(ctx.expression());
        } else {
            // TODO(matthew-c21) - Comments throw exceptions.
            throw new IllegalStateException("Unable to convert statement to node.");
        }
    }

    public Node get(LangParser.AssignmentContext ctx) {
        if (ctx.fnAssignment() != null) {
            return get(ctx.fnAssignment());
        } else if (ctx.varAssignment() != null) {
            return get(ctx.varAssignment());
        } else {
            throw new IllegalStateException("Unable to resolve assignment type.");
        }
    }

    public Node get(LangParser.VarAssignmentContext ctx) {
        boolean isConst = ctx.DEFMUT() != null;
        return new BindingNode(ctx.binding().getText(), get(ctx.expression()), isConst);
    }

    public Node get(LangParser.FnAssignmentContext ctx) {
        // Functions may not be re-assigned.
        return new BindingNode(ctx.IDENTIFIER().getText(), get(ctx.lambda()), false);
    }

    public Node get(LangParser.ExpressionContext ctx) {
        // Handle parentheses.
        if (ctx.LPAREN() != null) {
            return get(ctx.expression(0));
        }

        // Logical inversion.
        if (ctx.NOT() != null) {
            return new LogicalInversionNode(get(ctx.expression(0)));
        }

        // Handle binary operators.
        if (ctx.op != null) {
            return BINOP_SUPPLIER.get(ctx.op.getText()).apply(get(ctx.left), get(ctx.right));
        }

        // Handle terminals.
        if (ctx.primitive() != null) {
            return get(ctx.primitive());
        }


        // Handle conditions. Currently ignores elif.
        if (ctx.conditional() != null) {
            return get(ctx.conditional());
        }

        if (ctx.IDENTIFIER() != null) {
            return new IdentifierNode(ctx.IDENTIFIER().getText());
        }

        if (ctx.lambda() != null) {
            return get(ctx.lambda());
        }

        // Otherwise, we're dealing with a function call, or collection. These aren't supported quite yet.
        throw new UnsupportedOperationException("Unable to manage " + ctx.getText());
    }

    public Node get(LangParser.PrimitiveContext ctx) {
        Data data;

        if (ctx.bool() != null) {
            data = LangBool.of(ctx.bool().getText());
        } else if (ctx.FLOAT() != null) {
            data = LangFloat.of(ctx.FLOAT().getText());
        } else if (ctx.INT() != null) {
            data = LangInt.of(ctx.INT().getText());
        } else if (ctx.STRING() != null) {
            data = LangString.of(ctx.STRING().getText());
        } else {
            throw new IllegalStateException("Illegal primitive.");
        }

        return new LiteralNode(data);
    }

    public Node get(LangParser.ConditionalContext ctx) {
        if (ctx.ELIF() != null) {
            throw new UnsupportedOperationException("Elifs don't do anything");
        }

        return new Conditional(get(ctx.statementBody(0)),
                get(ctx.statementBody(1)),
                get(ctx.expression(0)));
    }

    public Node get(LangParser.LambdaContext ctx) {
        return new FunctionCreationNode(
                get(ctx.statementBody()),
                ctx.tupleIdentifier().IDENTIFIER().stream().map(ParseTree::getText).collect(Collectors.toList())
        );
    }

    public Node get(LangParser.StatementBodyContext ctx) {
        List<Node> statements = new ArrayList<>();

        for (LangParser.StatementContext statement : ctx.statement()) {
            statements.add(get(statement.expression()));
        }

        return new StatementListNode(statements);
    }
}
