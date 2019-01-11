package prj.clark.alchemy.err;

public class InvalidIdentifierException extends LangException {
    public InvalidIdentifierException() {
        super();
    }

    public InvalidIdentifierException(String msg) {
        super(msg);
    }

    public InvalidIdentifierException(Throwable t) {
        super(t);
    }

    public InvalidIdentifierException(String msg, Throwable t) {
        super(msg, t);
    }
}
