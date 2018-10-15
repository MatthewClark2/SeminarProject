package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangFloat;
import prj.clark.lang.impl.env.LangInt;

public class ModulusNode extends NumericBinaryOperator {
    public ModulusNode(Node left, Node right) {
        super(left, right, (l, r) -> {
            if (l instanceof LangFloat || r instanceof LangFloat) {
                return LangFloat.of(Double.parseDouble(l.toString()) % Double.parseDouble(r.toString()));
            } else {
                return LangInt.of(Long.parseLong(l.toString()) % Long.parseLong(r.toString()));
            }
        });
    }
}