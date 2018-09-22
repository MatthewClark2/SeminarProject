package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicData;

import java.util.ArrayList;
import java.util.List;

public class IdentifierNode extends ExpressionNode {
    private String identifier;
    private ExpressionNode value;

    @Override
    public BasicData evaluate() {
        return value.evaluate();
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<Node>(){{
            add(value);
        }};
    }
}
