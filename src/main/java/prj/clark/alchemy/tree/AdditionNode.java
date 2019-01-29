package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;

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
            if (l instanceof AlchemyFloat || r instanceof AlchemyFloat) {
                return AlchemyFloat.of(Double.parseDouble(l.toString()) + Double.parseDouble(r.toString()));
            } else {
                return AlchemyInt.of(Long.parseLong(l.toString()) + Long.parseLong(r.toString()));
            }
        });
    }
}
