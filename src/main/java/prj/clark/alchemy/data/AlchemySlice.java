package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Iterator;
import java.util.Optional;

public class AlchemySlice implements AlchemyList {
    private static final long INFINITE_LENGTH = -1;

    private Sliceable coll;
    private Numeric start;
    private Numeric stop;
    private Numeric skip;
    private long n;

    public static class AlchemySliceBuilder {
        private Sliceable coll;
        private Numeric start;
        private Numeric stop;
        private Numeric skip;

        public AlchemySliceBuilder(Sliceable coll) {
            this.coll = coll;
            start = AlchemyInt.of(0);
            stop = AlchemyFloat.of(Double.POSITIVE_INFINITY);
            skip = AlchemyInt.of(1);
        }

        public AlchemySliceBuilder setStart(Numeric start) {
            this.start = start;
            return this;
        }

        public AlchemySliceBuilder setStop(Numeric stop) {
            this.stop = stop;
            return this;
        }

        public AlchemySliceBuilder setSkip(Numeric skip) {
            this.skip = skip;
            return this;
        }

        public AlchemySlice build() {
            return new AlchemySlice(coll, start, stop, skip);
        }
    }

    private AlchemySlice(Sliceable coll, Numeric start, Numeric stop, Numeric skip) {
        // All three should be integers. However, stop can also be infinity, which may only be represented in floating point.
        if (!start.isInteger() || (stop.floatValue() != Double.POSITIVE_INFINITY && !stop.isInteger()) || !skip.isInteger()) {
            throw new TypeMismatchException();
        }

        this.coll = coll;
        this.start = start;
        this.stop = stop;
        this.skip = skip;

        if (stop.floatValue() == Float.POSITIVE_INFINITY) {
            n = -1;
        } else {
            n = (long) Math.ceil((stop.floatValue() - start.floatValue()) / skip.floatValue());
        }
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Data> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public Iterator<Data> iterator() {
        return new SliceIterator(start, stop, skip, coll);
    }

    @Override
    public Sequenced slice(Numeric start, Numeric end, Numeric n) {
        return () -> new SliceIterator(start, end, n, this);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        // For now, just iterate to find it.
        if (!(index instanceof Numeric) || !((Numeric) index).isInteger()) {
            throw new TypeMismatchException();
        }

        Numeric ind = (Numeric) index;

        if (n != INFINITE_LENGTH && (ind.intValue() >= n || ind.intValue() >= stop.intValue())) {
            return Optional.empty();
        }

        return coll.getIndex(AlchemyInt.of(start.intValue() + ind.intValue() * skip.intValue()));
    }
}
