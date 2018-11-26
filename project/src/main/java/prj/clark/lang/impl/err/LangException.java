package prj.clark.lang.impl.err;

/**
 * TODO(matthew-c21) - Managing these exceptions is turning into a hassle when dealing with the abstract parts of the
 * interpreter. Find a way to make it less painful to work with. The same holds true when dealing with {@link StaticLangException}.
 */
public abstract class LangException extends Exception {
    protected LangException() {
        super();
    }

    protected LangException(String msg) {
        super(msg);
    }

    protected LangException(Throwable t) {
        super(t);
    }

    protected LangException(String msg, Throwable t) {
        super(msg, t);
    }
}
