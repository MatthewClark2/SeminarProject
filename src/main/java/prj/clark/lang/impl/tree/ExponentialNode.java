package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangFloat;

public class ExponentialNode extends NumericBinaryOperator {
    public ExponentialNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangFloat.of(Math.pow(Double.parseDouble(l.toString()), Double.parseDouble(r.toString())))
        );
    }
}