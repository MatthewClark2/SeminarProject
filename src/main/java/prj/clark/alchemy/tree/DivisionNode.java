package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.Numeric;

public class DivisionNode extends NumericBinaryOperator {
    public DivisionNode(Valued left, Valued right) {
        super(left, right, (l,r) -> {
            Numeric a = (Numeric) l;
            Numeric b = (Numeric) r;
            return AlchemyFloat.of(a.floatValue() / b.floatValue());
        });
    }
}
