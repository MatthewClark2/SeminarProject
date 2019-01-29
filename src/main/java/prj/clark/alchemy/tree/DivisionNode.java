package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;

public class DivisionNode extends NumericBinaryOperator {
    public DivisionNode(Valued left, Valued right) {
        super(left, right, (l,r) ->
            AlchemyFloat.of(Double.parseDouble(l.toString()) / Double.parseDouble(r.toString()))
        );
    }
}
