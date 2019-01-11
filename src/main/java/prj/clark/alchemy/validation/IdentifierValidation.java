package prj.clark.alchemy.validation;

import prj.clark.alchemy.err.InvalidIdentifierException;

/**
 * Validates identifiers for the currently unnamed language. Follows standard Java conventions.
 */
public class IdentifierValidation {
    private static final String UNBOUND = "_";

    public static void validate(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new InvalidIdentifierException("EmptyType identifier");
        }

        if (!Character.isJavaIdentifierStart(identifier.charAt(0))) {
            throw new InvalidIdentifierException(identifier);
        }

        for (char ch : identifier.toCharArray()) {
            if (!Character.isJavaIdentifierPart(ch) || ch == '$') {
                throw new InvalidIdentifierException(identifier);
            }
        }
    }

    public static boolean isUnboundIdentifier(String identifier) {
        return UNBOUND.equals(identifier);
    }
}
