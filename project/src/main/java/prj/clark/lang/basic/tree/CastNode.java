package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.*;

import java.util.List;

public class CastNode extends ExpressionNode {
    private ExpressionNode expression;
    private String cast;

    public CastNode(ExpressionNode expression, String cast) {
        this.expression = expression;
        this.cast = cast;
    }

    @Override
    public List<Node> getChildren() {
        // This is a terminal node.
        return null;
    }

    @Override
    public BasicData evaluate(BasicContext ctx) {
        String rawData = expression.evaluate(ctx).getContent();

        switch (cast) {
            case "String": return new StringData(rawData);
            case "Int": {
                // This is a special case, as we may need to trim some information away.
                long data = (long) Double.parseDouble(rawData);
                return new IntegerData(data);
            }
            case "Float": return new DecimalData(rawData);
            default: throw new IllegalStateException("Cannot resolve cast: " + cast);
        }
    }
}
