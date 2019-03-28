package prj.clark.alchemy.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class AlchemyDict implements Sequenced, Indexed, Printable {
    private final Map<Data, Data> data;

    public AlchemyDict(Map<Data, Data> data) {
        this.data = new HashMap<>(data);
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public boolean terminates() {
        return false;
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
