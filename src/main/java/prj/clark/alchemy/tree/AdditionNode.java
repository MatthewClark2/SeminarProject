package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.Numeric;

/**
 * Represents addition.
 *
 * You'll note that this class (as well as the other binary operators) are
 * reliant on parsing the string representation of data. While not an ideal
 * solution, it does avoid a ton of casting and runtime type checking.
 */
public class AdditionNode extends NumericBinaryOperator {
    public AdditionNode(Valued left, Valued right) {
        super(left, right, (l, r) -> {
            Numeric a = (Numeric) l;
            Numeric b = (Numeric) r;
            if (a.isInteger() && b.isInteger()) {
                return AlchemyInt.of(a.intValue() + b.intValue());
            } else {
                return AlchemyFloat.of(a.floatValue() + b.floatValue());
            }
        });
    }
}
