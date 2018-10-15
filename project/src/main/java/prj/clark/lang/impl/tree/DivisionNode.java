package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.LangFloat;
import prj.clark.lang.impl.env.LangInt;

import java.util.function.BiFunction;

public class DivisionNode extends NumericBinaryOperator {
    public DivisionNode(Node left, Node right) {
        super(left, right, (l,r) -> {
            if (l instanceof LangFloat || r instanceof LangFloat) {
                return LangFloat.of(Double.parseDouble(l.toString()) / Double.parseDouble(r.toString()));
            } else {
                return LangInt.of(Long.parseLong(l.toString()) / Long.parseLong(r.toString()));
            }
        });
    }
    protected DivisionNode(Node left, Node right, BiFunction<Data, Data, Data> op) {
        super(left, right, op);
    }
}
