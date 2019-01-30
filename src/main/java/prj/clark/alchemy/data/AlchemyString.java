package prj.clark.alchemy.data;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlchemyString implements Sequenced, Sliceable, Printable {
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
}
