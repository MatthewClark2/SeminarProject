package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

public class SliceNode extends ReferentiallyTransparentValuedNode {
    private Valued coll;
    private Valued start;
    private Valued stop;
    private Valued skip;

    public static class SliceNodeBuilder {
        private Valued coll;
        private Valued start;
        private Valued stop;
        private Valued skip;

        public SliceNodeBuilder(Valued coll) {
            this.coll = coll;
            start = new LiteralNode(AlchemyInt.of(0));
            stop = new LiteralNode(AlchemyFloat.of(Double.POSITIVE_INFINITY));
            skip = new LiteralNode(AlchemyInt.of(1));
        }

        public SliceNodeBuilder setStart(Valued start) {
            this.start = start;
            return this;
        }

        public SliceNodeBuilder setStop(Valued stop) {
            this.stop = stop;
            return this;
        }

        public SliceNodeBuilder setSkip(Valued skip) {
            this.skip = skip;
            return this;
        }

        public SliceNode build() {
            return new SliceNode(coll, start, stop, skip);
        }
    }


    private SliceNode(Valued coll, Valued start, Valued stop, Valued skip) {
        this.coll = coll;
        this.start = start;
        this.stop = stop;
        this.skip = skip;
    }

    @Override
    public Data evaluate(Context ctx) {
        Data c = coll.evaluate(ctx);
        Data init = start.evaluate(ctx);
        Data end = stop.evaluate(ctx);
        Data n = skip.evaluate(ctx);

        if (!(c instanceof Sliceable)
                || !(init instanceof Numeric)
                || !(end instanceof Numeric)
                || !(n instanceof Numeric)) {
            throw new TypeMismatchException();
        }

        return new AlchemySlice.AlchemySliceBuilder((Sliceable)c)
                .setStart((Numeric) init)
                .setStop((Numeric) end)
                .setSkip((Numeric) n)
                .build();
    }
}
