package prj.clark.alchemy.data;

import java.util.Iterator;

public class ConcatenatedSequence implements Sequenced {
    private final Data initialValue;
    private final Sequenced source;

    private class ConcatenationIterator implements Iterator<Data> {
        private Data initialValue;
        private Iterator<Data> source;
        private boolean yieldedInitialValue = false;

        ConcatenationIterator(Data initialValue, Sequenced source) {
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

            return initialValue;
        }
    }

    public ConcatenatedSequence(Data initialValue, Sequenced source) {
        this.initialValue = initialValue;
        this.source = source;
    }

    @Override
    public Iterator<Data> iterator() {
        return new ConcatenationIterator(initialValue, source);
    }
}
