package prj.clark.lang.impl.err;

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
