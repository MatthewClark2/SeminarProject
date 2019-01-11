package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangFloat;
import prj.clark.alchemy.env.LangInt;

/**
 * Represents addition.
 *
 * You'll note that this class (as well as the other binary operators) are
 * reliant on parsing the string representation of data. While not an ideal
 * solution, it does avoid a ton of casting and runtime type checking.
 */
public class AdditionNode extends NumericBinaryOperator {
    public AdditionNode(Node left, Node right) {
        super(left, right, (l, r) -> {
            if (l instanceof LangFloat || r instanceof LangFloat) {
                return LangFloat.of(Double.parseDouble(l.toString()) + Double.parseDouble(r.toString()));
            } else {
                return LangInt.of(Long.parseLong(l.toString()) + Long.parseLong(r.toString()));
            }
        });
    }
}
