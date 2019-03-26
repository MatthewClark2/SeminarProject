package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Iterator;
import java.util.Optional;

public class AlchemyRange implements AlchemyList {
    private static final long INFINITE_LENGTH = -1;

    private Numeric start;
    private Numeric stop;
    private Numeric skip;
    private boolean isFloat;
    private long n;

    public static class AlchemyRangeBuilder {
        private Numeric start;
        private Numeric stop;
        private Numeric skip;

        public AlchemyRangeBuilder() {
            start = AlchemyInt.of(0);
            stop = AlchemyFloat.of(Double.POSITIVE_INFINITY);
            skip = AlchemyInt.of(1);
        }

        public AlchemyRangeBuilder setSkip(Numeric skip) {
            this.skip = skip;
            return this;
        }

        public AlchemyRangeBuilder setStart(Numeric start) {
            this.start = start;
            return this;
        }

        public AlchemyRangeBuilder setStop(Numeric stop) {
            this.stop = stop;
            return this;
        }

        public AlchemyRange build() {
            boolean isFloat = ! (start.isInteger() && skip.isInteger());
            return new AlchemyRange(start, stop, skip, isFloat);
        }
    }

    private static class AlchemyRangeIterator implements Iterator<Data> {
        private final AlchemyRange range;
        private Numeric index;

        AlchemyRangeIterator(AlchemyRange range) {
            this.range = range;
            this.index = range.start;
        }

        @Override
        public boolean hasNext() {
            if (range.isFloat) {
                return index.floatValue() < range.stop.floatValue();
            }

            return index.intValue() < range.stop.floatValue();
        }

        @Override
        public Data next() {
            Data d = range.getIndex(index).get();

            if (range.isFloat) {
                index = AlchemyFloat.of(index.floatValue() + range.skip.floatValue());
            } else {
                index = AlchemyInt.of(index.intValue() + range.skip.intValue());
            }

            return d;
        }
    }

    private AlchemyRange(Numeric start, Numeric stop, Numeric skip, boolean isFloat) {
        this.start = start;
        this.stop = stop;
        this.skip = skip;
        this.isFloat = isFloat;
        this.n = stop.floatValue() == Double.POSITIVE_INFINITY ?
                INFINITE_LENGTH
                : ((long) Math.ceil((stop.floatValue() - start.floatValue()) / skip.floatValue()));
    }

    @Override
    public String print() {
        return null;
    }

    @Override
    public Iterator<Data> iter() {
        return new AlchemyRangeIterator(this);
    }

    @Override
    public Sequenced slice(Numeric start, Numeric end, Numeric n) {
        return () -> new SliceIterator(start, end, n, this);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        if (!(index instanceof Numeric)) {
            throw new TypeMismatchException();
        }

        Numeric i = (Numeric) index;

        if (! i.isInteger()) {
            throw new IllegalArgumentException();
        }

        if (i.intValue() >= n && n != INFINITE_LENGTH) {
            return Optional.empty();
        }

        // Calculate the skip value.
        Data d = isFloat ?
                AlchemyFloat.of(start.floatValue() + skip.floatValue() * i.floatValue())
                : AlchemyInt.of(start.intValue() + skip.intValue() * i.intValue());

        return Optional.of(d);
    }
}
