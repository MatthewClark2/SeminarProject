package prj.clark.alchemy.data;

import java.util.Iterator;

public class SliceIterator implements Iterator<Data> {
    private Numeric start;
    private Numeric end;
    private Numeric skip;
    private Sliceable coll;
    private Numeric pointer;

    public SliceIterator(Numeric start, Numeric end, Numeric skip, Sliceable coll) {
        this.start = start;
        this.end = end;
        this.skip = skip;
        this.coll = coll;
        this.pointer = start;
    }

    @Override
    public boolean hasNext() {
        return pointer != null && coll.getIndex(pointer).isPresent();
    }

    @Override
    public Data next() {
        Data d = coll.getIndex(pointer).get();

        if (pointer.intValue() + skip.intValue() < end.intValue()) {
            pointer = AlchemyInt.of(pointer.intValue() + skip.intValue());
        } else {
            pointer = null;
        }

        return d;
    }
}
