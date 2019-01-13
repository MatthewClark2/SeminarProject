package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.LangBool;

public class GreaterThanEqualNode extends NumericBinaryOperator {

    public GreaterThanEqualNode(Node left, Node right) {
        super(left, right, (l, r) ->
                LangBool.of(Double.parseDouble(l.toString()) >= Double.parseDouble(r.toString())));
    }
}
