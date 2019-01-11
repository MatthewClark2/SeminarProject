package prj.clark.lang.impl.err;

public class NoSuchBindingException extends LangException {

    public NoSuchBindingException() {
        super();
    }

    public NoSuchBindingException(String msg) {
        super(msg);
    }

    public NoSuchBindingException(Throwable t) {
        super(t);
    }

    public NoSuchBindingException(String msg, Throwable t) {
        super(msg, t);
    }
}
