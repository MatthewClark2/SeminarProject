package prj.clark.alchemy.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AlchemyTuple implements Sequenced, Indexed, Printable {
    private final List<Data> data;

    public AlchemyTuple(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return Optional.empty();
    }

    @Override
    public String print() {
        return null;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
