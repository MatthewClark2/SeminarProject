package prj.clark.alchemy.err;

public class StringFormatException extends LangException {

    public StringFormatException() {
        super();
    }

    public StringFormatException(String msg) {
        super(msg);
    }

    public StringFormatException(Throwable t) {
        super(t);
    }

    public StringFormatException(String msg, Throwable t) {
        super(msg, t);
    }
}
