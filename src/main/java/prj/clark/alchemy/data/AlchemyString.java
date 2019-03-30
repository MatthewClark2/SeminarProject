package prj.clark.alchemy.data;

import prj.clark.alchemy.err.StringFormatException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlchemyString implements Sequenced, Sliceable, Printable {
    // Keeps track of the number of characters needed to transform each each escape.
    private static final Map<Character, Integer> ESCAPE_MAP;

    private final String value;
    private final EagerAlchemyList chars;

    static {
        ESCAPE_MAP = new HashMap<>();
        ESCAPE_MAP.put('r', 2);
        ESCAPE_MAP.put('f', 2);
        ESCAPE_MAP.put('t', 2);
        ESCAPE_MAP.put('n', 2);
        ESCAPE_MAP.put('\\', 2);
        ESCAPE_MAP.put('"', 2);
        ESCAPE_MAP.put('\'', 2);
        ESCAPE_MAP.put('b', 2);
        ESCAPE_MAP.put('u', 6);
    }

    private AlchemyString(String value) {
        this.value = value;
        chars = new EagerAlchemyList(value.codePoints().mapToObj(AlchemyCharacter::of).collect(Collectors.toList()));
    }

    public static AlchemyString of(String content) {
        StringBuilder sb = new StringBuilder();
        /*
        for each character in the string:
            if the character is the escape character:
                if there isn't another character:
                    throw exception
                if the next character not in escape_map
                    throw exception
                else
                    add new alchemy character based on substring from current through current + escape[next].
            else
                add it directly
         */
        for (int i = 0; i < content.length(); ++i) {
            if (content.charAt(i) == '\\') {
                if (i == content.length() - 1) {
                    throw new StringFormatException("Cannot have single escape character at end of string.");
                }

                char next = content.charAt(i + 1);

                if (ESCAPE_MAP.containsKey(next)) {
                    String escape = content.substring(i, i + ESCAPE_MAP.get(next));
                    sb.append(AlchemyCharacter.of(escape));
                    i += ESCAPE_MAP.get(next) - 1;
                } else {
                    throw new StringFormatException("Unrecognized escape character: " + next);
                }
            } else {
                sb.append(content.charAt(i));
            }
        }
        return new AlchemyString(sb.toString());
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

    // Implement these using an EagerAlchemyList.

    @Override
    public Iterator<Data> iterator() {
        return chars.iterator();
    }

    @Override
    public boolean terminates() {
        return true;
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return chars.getIndex(index);
    }
}
