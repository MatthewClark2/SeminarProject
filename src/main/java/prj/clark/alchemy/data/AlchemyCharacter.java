package prj.clark.alchemy.data;

/**
 * This class is used to wrap UTF-16 characters as Alchemy {@link Data}.
 *
 * Characters are always considered truthy.
 */
public class AlchemyCharacter implements Printable {
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

    @Override
    public String print() {
        return representation;
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
