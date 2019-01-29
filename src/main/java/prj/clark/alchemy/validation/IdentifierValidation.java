package prj.clark.alchemy.validation;

import prj.clark.alchemy.err.LangException;

/**
 * Validates identifiers for the currently unnamed language. Follows standard Java conventions.
 */
public class IdentifierValidation {
    // TODO(matthew-c21) - Either deprecate or move this class.
    public static class InvalidIdentifierException extends LangException {
        public InvalidIdentifierException(String msg) {
            super(msg);
        }
    }

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
}
