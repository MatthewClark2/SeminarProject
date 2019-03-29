package prj.clark.alchemy.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcatenatedSequence implements Sequenced {
    private final Data prependedValue;
    private final Sequenced source;

    private static class ConcatenationIterator implements Iterator<Data> {
        private Data prependedValue;
        private Iterator<Data> source;
        private boolean yieldedInitialValue = false;

        ConcatenationIterator(Data prependedValue, Sequenced source) {
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

    private ConcatenatedSequence(Data prependedValue, Sequenced source) {
        this.prependedValue = prependedValue;
        this.source = source;
    }

    public static Sequenced concat(Data prependedValue, Sequenced source) {
        return new ConcatenatedSequence(prependedValue, source);
    }

    public static Sequenced concat(Sequenced source, Data appendedValue) {
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
}
