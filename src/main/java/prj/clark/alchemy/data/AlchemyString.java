package prj.clark.alchemy.data;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlchemyString implements Sequenced, Sliceable, Printable {
    private final String value;
    private final AlchemyList chars;

    private AlchemyString(String value) {
        this.value = value;
        chars = new AlchemyList(value.codePoints().mapToObj(AlchemyCharacter::of).collect(Collectors.toList()));
    }

    public static AlchemyString of(String content) {
        return new AlchemyString(content);
    }

    @Override
    public String print() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlchemyString) {
            AlchemyString s = (AlchemyString) o;
            return s.value.equals(this.value);
        }

        return false;
    }

    @Override
    public boolean toBoolean() {
        return !value.isEmpty();
    }

    // Implement these using an AlchemyList.

    @Override
    public Iterator<Data> iter() {
        return chars.iter();
    }

    @Override
    public Sequenced slice(long start, long end, long n) {
        return chars.slice(start, end, n);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return chars.getIndex(index);
    }
}
