package prj.clark.alchemy.data;

import java.util.Iterator;
import java.util.Optional;

public class AlchemyString implements Sequenced, Sliceable, Printable {
    private final String value;

    @Override
    public String print() {
        return value;
    }

    // Implement these using an AlchemyList.
    @Override
    public Iterator<Data> iter() {
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

    private AlchemyString(String value) {
        this.value = value;
    }

    public static Data of(String content) {
        return new AlchemyString(content);
    }
}
