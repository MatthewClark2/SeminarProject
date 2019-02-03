package prj.clark.alchemy.data;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlchemyString implements Sequenced<AlchemyCharacter>, Sliceable, Printable {
    private final String value;
    // TODO(matthew-c21) - Update to use an AlchemyList of AlchemyChars.
    private final List<AlchemyCharacter> chars;

    private AlchemyString(String value) {
        this.value = value;
        chars = value.codePoints().mapToObj(AlchemyCharacter::of).collect(Collectors.toList());;
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
    public Iterator<AlchemyCharacter> iter() {
        return chars.iterator();
    }

    @Override
    public Sequenced slice(Numeric start, Numeric end, Numeric n) {
        return null;
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return Optional.empty();
    }
}
