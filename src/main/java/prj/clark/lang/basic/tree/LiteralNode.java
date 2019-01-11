package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

import java.util.List;

public class LiteralNode extends ExpressionNode {
    private BasicData data;

    public LiteralNode(BasicData data) {
        this.data = data;
    }

    @Override
    public BasicData evaluate(BasicContext ctx) {
        return data;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }
}
