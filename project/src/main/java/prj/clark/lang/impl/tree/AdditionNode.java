package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Float;
import prj.clark.lang.impl.env.Int;

public class AdditionNode extends NumericBinaryOperator {
    public AdditionNode(Node left, Node right) {
        super(left, right, (l, r) -> {

            if (l instanceof Float) {
                if (r instanceof Float) {
                    Float x = (Float) l;
                    Float y = (Float) r;

                    return new Float.of(x.getValue() + y.getValue());
                }
            } else if (r instanceof Float) {
                Int x = (Int) l;
                Float y = (Float) r;

                return new Float.of(x.getValue() + y.getValue());
            } else {
                Int x = (Int) l;
                Int y = (Int) r;
                return new Int.of(x.getValue() + y.getValue());
            }
        });
    }
}
