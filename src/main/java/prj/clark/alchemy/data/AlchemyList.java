package prj.clark.alchemy.data;

import java.util.List;
import java.util.Iterator;
import java.util.Optional;

public class AlchemyList implements Sequenced, Sliceable, Printable {
    private final List<Data> data;

    public AlchemyList(List<Data> data) {
        this.data = data;
    }

    @Override
    public Iterator iter() {
        return null;
    }

    @Override
    public Sequenced slice(Numeric start, Numeric end, Numeric n) {
        return null;
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return Optional.empty();
    }

    @Override
    public String print() {
        return null;
    }
}
