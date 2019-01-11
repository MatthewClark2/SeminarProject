package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangFloat;

public class DivisionNode extends NumericBinaryOperator {
    public DivisionNode(Node left, Node right) {
        super(left, right, (l,r) ->
            LangFloat.of(Double.parseDouble(l.toString()) / Double.parseDouble(r.toString()))
        );
    }
}
