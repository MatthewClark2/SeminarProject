package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class LessThanEqualNode extends NumericBinaryOperator {

    public LessThanEqualNode(Node left, Node right) {
        super(left, right, (l, r) ->
                AlchemyBoolean.of(Double.parseDouble(l.toString()) <= Double.parseDouble(r.toString())));
    }
}
