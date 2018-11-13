package prj.clark.lang.impl.err;

public class IllegalRebindingException extends LangException {
    public IllegalRebindingException() {
        super();
    }

    public IllegalRebindingException(String msg) {
        super(msg);
    }

    public IllegalRebindingException(Throwable t) {
        super(t);
    }

    public IllegalRebindingException(String msg, Throwable t) {
        super(msg, t);
    }
}
