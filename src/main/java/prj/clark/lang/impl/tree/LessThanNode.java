package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangBool;

public class LessThanNode extends NumericBinaryOperator {

    public LessThanNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangBool.of(Double.parseDouble(l.toString()) < Double.parseDouble(r.toString())));
    }
}
