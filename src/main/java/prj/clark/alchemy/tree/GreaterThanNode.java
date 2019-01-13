package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.LangBool;

public class GreaterThanNode extends NumericBinaryOperator {

    public GreaterThanNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangBool.of(Double.parseDouble(l.toString()) > Double.parseDouble(r.toString())));
    }
}
