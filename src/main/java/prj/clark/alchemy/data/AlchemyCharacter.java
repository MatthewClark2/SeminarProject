package prj.clark.alchemy.data;

import prj.clark.alchemy.err.StringFormatException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to wrap UTF-16 characters as Alchemy {@link Data}.
 *
 * Characters are always considered truthy.
 */
public class AlchemyCharacter implements Printable {
    private static final Map<Character, Character> ESCAPE_MAP;

    static {
        ESCAPE_MAP = new HashMap<>();
        ESCAPE_MAP.put('r', '\r');
        ESCAPE_MAP.put('f', '\f');
        ESCAPE_MAP.put('t', '\t');
        ESCAPE_MAP.put('n', '\n');
        ESCAPE_MAP.put('\\', '\\');
        ESCAPE_MAP.put('"', '"');
        ESCAPE_MAP.put('\'', '\'');
        ESCAPE_MAP.put('b', '\b');
    }

    private final int codePoint;
    // We hold this for caching purposes.
    private final String representation;

    private AlchemyCharacter(int codePoint) {
        this.codePoint = codePoint;
        representation = new String(Character.toChars(codePoint));
    }

    public static AlchemyCharacter of(int codePoint) {
        return new AlchemyCharacter(codePoint);
    }

    public static AlchemyCharacter of(char ch) {
        return new AlchemyCharacter(ch);
    }

    public static AlchemyCharacter of(byte b) {
        return new AlchemyCharacter(b);
    }

    public static AlchemyCharacter of(String value) {
        if (value.length() == 0) {
            throw new StringFormatException("Cannot make character from empty string.");
        }

        if (value.length() == 1) {
            return AlchemyCharacter.of(value.charAt(0));
        }

        if (value.matches("^\\\\.$")) {
            // The string is just a single escape.
            char next = value.charAt(1);

            if (ESCAPE_MAP.containsKey(next)) {
                return new AlchemyCharacter(ESCAPE_MAP.get(next));
            } else {
                throw new StringFormatException("Unrecognized escape character: " + next);
            }
        } else if (value.matches("^\\\\u[a-fA-F0-9]{4}$")) {
            String hexCode = value.substring(2);
            return AlchemyCharacter.of(Integer.parseInt(hexCode, 16));
        } else {
            throw new StringFormatException("Unrecognized character format.");
        }
    }

    @Override
    public String print() {
        return representation;
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public int hashCode() {
        return codePoint;
    }

    @Override
    public boolean toBoolean() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlchemyCharacter) {
            AlchemyCharacter c = (AlchemyCharacter) o;
            return c.codePoint == this.codePoint;
        }

        return false;
    }
}
