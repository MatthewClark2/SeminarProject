package prj.clark.lang.impl.err;

/**
 * Used to check for issues that occur before the actual execution phase.
 */
public class StaticLangException extends RuntimeException {
    protected StaticLangException() {
        super();
    }

    protected StaticLangException(String msg) {
        super(msg);
    }

    protected StaticLangException(Throwable t) {
        super(t);
    }

    protected StaticLangException(String msg, Throwable t) {
        super(msg, t);
    }
}
