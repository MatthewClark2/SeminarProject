package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Iterator;
import java.util.Optional;

public class AlchemyRange implements AlchemyList {
    private static final long INFINITE_LENGTH = -1;

    private Numeric start;
    private Numeric skip;
    private Numeric stop;
    private boolean isFloat;
    private long n;

    public static class AlchemyRangeBuilder {
        private Numeric first;
        private Numeric second;
        private Numeric stop;

        public AlchemyRangeBuilder() {
            first = AlchemyInt.of(0);
            second = AlchemyInt.of(1);
            stop = AlchemyFloat.of(Double.POSITIVE_INFINITY);
        }

        public AlchemyRangeBuilder setFirst(Numeric first) {
            this.first = first;
            return this;
        }

        public AlchemyRangeBuilder setSecond(Numeric second) {
            this.second = second;
            return this;
        }

        public AlchemyRangeBuilder setStop(Numeric stop) {
            this.stop = stop;
            return this;
        }

        public AlchemyRange build() {
            boolean isFloat = ! (first.isInteger() && second.isInteger());
            return new AlchemyRange(first, second, stop, isFloat);
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

    private AlchemyRange(Numeric first, Numeric second, Numeric stop, boolean isFloat) {
        this.start = first;
        this.stop = stop;
        this.skip = isFloat ?
                AlchemyFloat.of(second.floatValue() - first.floatValue())
                : AlchemyInt.of(second.intValue() - first.intValue());

        this.isFloat = isFloat;

        // Determine the number of elements in the range.
        if (second.floatValue() - first.floatValue() <= 0) {
            this.n = 0;
        } else if (stop.floatValue() == Double.POSITIVE_INFINITY) {
            this.n = INFINITE_LENGTH;
        } else {
            this.n = stop.floatValue() == Double.POSITIVE_INFINITY ?
                    INFINITE_LENGTH
                    : ((long) Math.ceil((stop.floatValue() - start.floatValue()) / skip.floatValue()));
        }
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
