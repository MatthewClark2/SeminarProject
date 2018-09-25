package prj.clark.lang.basic.env;

import java.io.Reader;

/**
 * This exists due to the complexity of implementing the javax.script set of interfaces.
 */
public interface Evaluator {
    BasicContext getContext();
    BasicData eval(String script);
    BasicData eval(String script, BasicContext ctx);
    BasicData eval(Reader in);
    BasicData eval(Reader in, BasicContext ctx);
}
