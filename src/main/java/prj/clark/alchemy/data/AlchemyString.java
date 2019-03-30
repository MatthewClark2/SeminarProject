package prj.clark.alchemy.data;

import prj.clark.alchemy.err.StringFormatException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlchemyString implements Sequenced, Sliceable, Printable {
    private static final Map<String, String> ESCAPE_MAP;

    private final String value;
    private final EagerAlchemyList chars;

    static {
        ESCAPE_MAP = new HashMap<>();
        ESCAPE_MAP.put("r", "\r");
        ESCAPE_MAP.put("f", "\f");
        ESCAPE_MAP.put("t", "\t");
        ESCAPE_MAP.put("n", "\n");
        ESCAPE_MAP.put("\\", "\\");
        ESCAPE_MAP.put("\"", "\"");
        ESCAPE_MAP.put("'", "'");
        ESCAPE_MAP.put("b", "\b");
    }

    private AlchemyString(String value) {
        StringBuilder sb = new StringBuilder();
        /*
        for each character in the string:
            if the character is the escape character:
                if there isn't another character:
                    throw exception
                if the next character is u:
                    consume the next four characters
                    if they don't match a hex input:
                        throw exception
                    escape them, and add the escaped value to the builder
                    skip the next four
                else if the next character isn't in the escape list:
                    throw exception
                add the escaped character
                skip the next one
            else
                add it directly
         */
        for (int i = 0; i < value.length(); ++i) {
            if (value.charAt(i) == '\\') {
                if (i == value.length() - 1) {
                    throw new StringFormatException("Cannot have single escape character at end of string.");
                }

                char next = value.charAt(i + 1);
                if (next == 'u') {
                    if (i + 6 <= value.length()) {
                        String hexCode = value.substring(i + 2, i + 6);
                        if (hexCode.matches("[A-Fa-f0-9]")) {
                            // TODO(fix unicode interpolation.
                            throw new UnsupportedOperationException("Creating string ESCAPE_MAP is harder than I thought.");
                        }
                    } else {
                        throw new StringFormatException("Not enough characters provided for unicode escape.");
                    }
                } else if (ESCAPE_MAP.containsKey("" + next)) {
                    sb.append(ESCAPE_MAP.get("" + next));
                    i++;
                } else {
                    throw new StringFormatException("Unrecognized string escape: " + next);
                }
            } else {
                sb.append(value.charAt(i));
            }
        }

        this.value = sb.toString();

        chars = new EagerAlchemyList(value.codePoints().mapToObj(AlchemyCharacter::of).collect(Collectors.toList()));
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
