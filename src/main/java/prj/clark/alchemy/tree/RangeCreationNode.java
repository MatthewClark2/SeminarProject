package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

public class RangeCreationNode extends ReferentiallyTransparentValuedNode {
    private Valued first;
    private Valued second;
    private Valued last;

    /**
     * Create a new RangeCreationNode. If null is passed for one of the parameters, then that parameter will default to
     * some value during execution.
     * @param first the range's initial value.
     * @param second the range's second value. Establishes the skip length.
     * @param last the final value in the range, non-inclusive.
     */
    public RangeCreationNode(Valued first, Valued second, Valued last) {
        this.first = first;
        this.second = second;
        this.last = last;
    }

    @Override
    public Data evaluate(Context ctx) {
        if (first == null) {
            first = new LiteralNode(AlchemyInt.of(0));
        }

        // Since the default value for the second argument depends on the first, initialization can't be checked until
        // execution.
        if (second == null) {
            Data base = first.evaluate(ctx);
            if (!(base instanceof Numeric)) {
                throw new TypeMismatchException();
            }

            if (!((Numeric) base).isInteger()) {
                second = new LiteralNode(AlchemyFloat.of(((Numeric)base).floatValue() + 1));
            } else {
                second = new LiteralNode(AlchemyInt.of(((Numeric)base).intValue() + 1));
            }
        }

        if (last == null) {
            last = new LiteralNode(AlchemyFloat.of(Double.POSITIVE_INFINITY));
        }

        Data one = first.evaluate(ctx);
        Data two = second.evaluate(ctx);
        Data end = last.evaluate(ctx);

        if (! (one instanceof Numeric && two instanceof Numeric && end instanceof Numeric)) {
            throw new TypeMismatchException();
        }

        return new AlchemyRange.AlchemyRangeBuilder()
                .setFirst((Numeric) first.evaluate(ctx))
                .setSecond((Numeric) second.evaluate(ctx))
                .setStop((Numeric) last.evaluate(ctx))
                .build();
    }
}
