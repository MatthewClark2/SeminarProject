package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangFloat;

public class DivisionNode extends NumericBinaryOperator {
    public DivisionNode(Node left, Node right) {
        super(left, right, (l,r) ->
            LangFloat.of(Double.parseDouble(l.toString()) / Double.parseDouble(r.toString()))
        );
    }
}
