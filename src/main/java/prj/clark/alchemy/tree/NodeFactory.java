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
}
