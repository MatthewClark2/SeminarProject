package prj.clark.lang.math;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

public class MathErrorListener extends BaseErrorListener {
    private OutputStream os;
    private Object offendingSymbol;

    public MathErrorListener() {
        this(System.err);
    }

    public MathErrorListener(OutputStream os) {
        this.os = os;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        try {
            os.write(msg.getBytes());
        } catch (IOException e1) {
            throw new IOError(e1);
        } finally {
            this.offendingSymbol = offendingSymbol;
        }
    }

    public Object getSymbol() {
        return offendingSymbol;
    }
}
