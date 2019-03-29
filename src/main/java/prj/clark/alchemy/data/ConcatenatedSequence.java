package prj.clark.alchemy.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcatenatedSequence implements Chainable {
    private final Data prependedValue;
    private final Chainable source;

    private static class ConcatenationIterator implements Iterator<Data> {
        private Data prependedValue;
        private Iterator<Data> source;
        private boolean yieldedInitialValue = false;

        ConcatenationIterator(Data prependedValue, Chainable source) {
            this.prependedValue = prependedValue;
            this.source = source.iterator();
        }

        @Override
        public boolean hasNext() {
            return (! yieldedInitialValue) || source.hasNext();
        }

        @Override
        public Data next() {
            if (yieldedInitialValue) {
                return this.source.next();
            }

            yieldedInitialValue = true;

            return prependedValue;
        }
    }

    private ConcatenatedSequence(Data prependedValue, Chainable source) {
        this.prependedValue = prependedValue;
        this.source = source;
    }

    public static Chainable concat(Data prependedValue, Chainable source) {
        return new ConcatenatedSequence(prependedValue, source);
    }

    public static Chainable concat(Chainable source, Data appendedValue) {
        List<Data> data = new ArrayList<>();
        source.iterator().forEachRemaining(data::add);
        data.add(appendedValue);
        return new EagerAlchemyList(data);
    }

    @Override
    public Iterator<Data> iterator() {
        return new ConcatenationIterator(prependedValue, source);
    }

    @Override
    public boolean terminates() {
        return source.terminates();
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(prependedValue.toString());

        for (Data d : source) {
            sb.append(", ").append(d.toString());
        }

        return sb.append("]").toString();
    }

    @Override
    public String toString() {
        return print();
    }
}
