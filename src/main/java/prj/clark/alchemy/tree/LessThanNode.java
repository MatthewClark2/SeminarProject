package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class LessThanNode extends NumericBinaryOperator {

    public LessThanNode(Valued left, Valued right) {
        super(left, right, (l, r) ->
                AlchemyBoolean.of(Double.parseDouble(l.toString()) < Double.parseDouble(r.toString())));
    }
}
