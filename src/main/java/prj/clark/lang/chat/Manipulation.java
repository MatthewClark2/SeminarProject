package prj.clark.lang.chat;

// Helper class for various ANSI text manipulations.
public enum Manipulation {
    // Text modifications
    BOLD("bold", 1),
    UNDERLINE("underline", 4),

    // Colors
    BLACK("black", 30),
    RED("red", 31),
    GREEN("green", 32),
    YELLOW("yellow", 33),
    BLUE("blue", 34),
    MAGENTA("magenta", 35),
    CYAN("cyan", 36),
    WHITE("white", 37);

    public static final String RESET = "\u001b[0m";

    private final String name;
    private final String value;

    Manipulation(String name, int value) {
        this.name = name;
        this.value = "\u001b[" + value + "m";
    }

    public static String initialModification(String s, Manipulation... manipulations) {
        StringBuilder sb = new StringBuilder();

        for (Manipulation m : manipulations) {
            sb.append(m.value);
        }

        return sb.toString() + s;
    }

    public static String modify(String s, Manipulation... manipulations) {
        return initialModification(s, manipulations) + RESET;
    }

    public static String modify(String s, String... manipulations) {
        Manipulation[] ms = new Manipulation[manipulations.length];

        for (int i = 0; i < manipulations.length; ++i) {
            ms[i] = match(manipulations[i]);
        }

        return modify(s, ms);
    }

    public static Manipulation match(String c) {
        for (Manipulation col : values()) {
            if (col.name.equals(c)) {
                return col;
            }
        }

        throw new IllegalArgumentException(c + " is not a valid color.");
    }

    public String getValue() {
        return value;
    }
}
