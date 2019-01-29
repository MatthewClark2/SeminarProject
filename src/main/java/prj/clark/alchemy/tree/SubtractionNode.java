package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;

public class SubtractionNode extends NumericBinaryOperator {
    public SubtractionNode(Valued left, Valued right) {
        super(left, right, (l, r) -> {
            if (l instanceof AlchemyFloat || r instanceof AlchemyFloat) {
                return AlchemyFloat.of(Double.parseDouble(l.toString()) - Double.parseDouble(r.toString()));
            } else {
                return AlchemyInt.of(Long.parseLong(l.toString()) - Long.parseLong(r.toString()));
            }
        });
    }
}
