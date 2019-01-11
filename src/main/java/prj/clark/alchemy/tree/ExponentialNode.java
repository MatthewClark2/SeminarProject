package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangFloat;

public class ExponentialNode extends NumericBinaryOperator {
    public ExponentialNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangFloat.of(Math.pow(Double.parseDouble(l.toString()), Double.parseDouble(r.toString())))
        );
    }
}