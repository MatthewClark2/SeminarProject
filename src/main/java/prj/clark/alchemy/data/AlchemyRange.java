package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Iterator;
import java.util.Optional;

public class AlchemyRange extends AlchemyList {
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
            // These checks are done just before construction to simplify the logic of making sure that the second
            // value is set correctly.
            if (first == null) {
                first = AlchemyInt.of(0);
            }

            if (second == null) {
                if (first.isInteger()) {
                    second = AlchemyInt.of(first.intValue() + 1);
                } else {
                    second = AlchemyFloat.of(first.floatValue() + 1);
                }
            }

            if (stop == null) {
                stop = AlchemyFloat.of(Double.POSITIVE_INFINITY);
            }

            boolean isFloat = ! (first.isInteger() && second.isInteger());
            return new AlchemyRange(first, second, stop, isFloat);
        }
    }

    private static class AlchemyRangeIterator implements Iterator<Data> {
        private final AlchemyRange range;
        private long index;

        AlchemyRangeIterator(AlchemyRange range) {
            this.range = range;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return range.n != INFINITE_LENGTH && index < range.n;
        }

        @Override
        public Data next() {
            return range.getIndex(AlchemyInt.of(index++)).get();
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
    public Iterator<Data> iterator() {
        return new AlchemyRangeIterator(this);
    }

    @Override
    public boolean terminates() {
        return n != INFINITE_LENGTH;
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        if (!(index instanceof Numeric)) {
            throw new TypeMismatchException();
        }

        Numeric i = (Numeric) index;

        if (! i.isInteger()) {
            throw new TypeMismatchException();
        }

        if (i.intValue() < 0 || (i.intValue() >= n && n != INFINITE_LENGTH)) {
            return Optional.empty();
        }

        // Calculate the skip value.
        Data d = isFloat ?
                AlchemyFloat.of(start.floatValue() + skip.floatValue() * i.floatValue())
                : AlchemyInt.of(start.intValue() + skip.intValue() * i.intValue());

        return Optional.of(d);
    }

    @Override
    public String toString() {
        return print();
    }
}
