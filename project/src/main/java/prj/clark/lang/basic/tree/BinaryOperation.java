package prj.clark.lang.basic.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class BinaryOperation extends ExpressionNode {
    protected ExpressionNode left;
    protected ExpressionNode right;

    @Override
    public List<Node> getChildren() {
        return new ArrayList<Node>(){{
            add(left);
            add(right);
        }};
    }
}
