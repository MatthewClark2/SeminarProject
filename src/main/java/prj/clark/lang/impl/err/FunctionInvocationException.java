package prj.clark.lang.impl.err;

public class FunctionInvocationException extends LangException {
    public FunctionInvocationException() {
        super();
    }

    public FunctionInvocationException(String msg) {
        super(msg);
    }

    public FunctionInvocationException(Throwable t) {
        super(t);
    }

    public FunctionInvocationException(String msg, Throwable t) {
        super(msg, t);
    }
}
