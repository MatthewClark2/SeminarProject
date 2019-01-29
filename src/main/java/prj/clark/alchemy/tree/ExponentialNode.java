package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;

public class ExponentialNode extends NumericBinaryOperator {
    public ExponentialNode(Valued left, Valued right) {
        super(left, right, (l, r) ->
                AlchemyFloat.of(Math.pow(Double.parseDouble(l.toString()), Double.parseDouble(r.toString())))
        );
    }
}