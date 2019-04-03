package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyInt;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Numeric;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

public class UnaryNegationNode extends ReferentiallyTransparentValuedNode {
    private final Valued v;

    public UnaryNegationNode(Valued valued) {
        this.v = valued;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data d = v.evaluate(ctx);

        if (! (d instanceof Numeric)) {
            throw new TypeMismatchException();
        }

        Numeric n = (Numeric) d;

        if (n.isInteger()) {
            return AlchemyInt.of(-n.intValue());
        } else {
            return AlchemyFloat.of(-n.floatValue());
        }
    }
}
