package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangBool;

public class LessThanNode extends NumericBinaryOperator {

    public LessThanNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangBool.of(Double.parseDouble(l.toString()) < Double.parseDouble(r.toString())));
    }
}
