package prj.clark.lang.impl.err;

public class TypeMismatchException extends LangException {

    public TypeMismatchException() {
        super();
    }

    public TypeMismatchException(String msg) {
        super(msg);
    }

    public TypeMismatchException(Throwable t) {
        super(t);
    }

    public TypeMismatchException(String msg, Throwable t) {
        super(msg, t);
    }
}
