package prj.clark.alchemy.tree;

import prj.clark.alchemy.AlchemyParser;

import java.util.*;
import java.util.function.BiFunction;

/**
 * This is basically just a sample class for converting the expression nodes of a parse tree into chunks of an AST.
 * It does not cover a solid chunk of the actual language.
 */
public class NodeFactory {
    private static final Map<String, BiFunction<Valued, Valued, Valued>> BINOP_SUPPLIER = new HashMap<>();

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
        // TODO(matthew-c21) - Add feed, access, or, and and.
    }

    public List<Node> getAll(AlchemyParser.FileContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.LineContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.AssignmentContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.BindingContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.DictContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.ExpressionContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.ExpressionListContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.LambdaContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.ListContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.StatementContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.StatementBodyContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.TupleContext ctx) {
        return null;
    }

    public Node get(AlchemyParser.TupleIdentifierContext ctx) {
        return null;
    }
}
