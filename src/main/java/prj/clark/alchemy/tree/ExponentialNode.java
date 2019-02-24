package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.Numeric;

public class ExponentialNode extends NumericBinaryOperator {
    public ExponentialNode(Valued left, Valued right) {
        super(left, right, (l, r) ->
                AlchemyFloat.of(Math.pow(((Numeric)l).floatValue(), ((Numeric)r).floatValue()))
        );
    }
}