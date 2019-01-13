package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.LangFloat;
import prj.clark.alchemy.data.LangInt;

public class MultiplicationNode extends NumericBinaryOperator {
    public MultiplicationNode(Node left, Node right) {
        super(left, right, (l, r) -> {
            if (l instanceof LangFloat || r instanceof LangFloat) {
                return LangFloat.of(Double.parseDouble(l.toString()) * Double.parseDouble(r.toString()));
            } else {
                return LangInt.of(Long.parseLong(l.toString()) * Long.parseLong(r.toString()));
            }
        });
    }
}
