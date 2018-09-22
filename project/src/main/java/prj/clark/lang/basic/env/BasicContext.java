package prj.clark.lang.basic.env;

import javax.script.SimpleScriptContext;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class BasicContext extends SimpleScriptContext {
    public BasicContext() {
        this(new InputStreamReader(System.in),
                new OutputStreamWriter(System.out),
                new OutputStreamWriter(System.err));
    }

    public BasicContext(Reader in, Writer out, Writer err) {
        super.setReader(in);
        super.setWriter(out);
        super.setErrorWriter(err);
    }
}
