package prj.clark.lang.basic.env;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import prj.clark.lang.basic.BasicLexer;
import prj.clark.lang.basic.BasicParser;
import prj.clark.lang.basic.tree.AbstractSyntaxTree;

import java.io.IOError;
import java.io.IOException;
import java.io.Reader;

public class BasicEvaluator implements Evaluator {
    private BasicContext ctx;

    public BasicEvaluator() {
        ctx = new BasicContext();
    }

    @Override
    public BasicContext getContext() {
        return ctx;
    }

    @Override
    public BasicData eval(String script) {
        return eval(script, ctx);
    }

    @Override
    public BasicData eval(String script, BasicContext ctx) {
        return eval(new ANTLRInputStream(script), ctx);
    }

    @Override
    public BasicData eval(Reader in) {
        return eval(in, ctx);
    }

    @Override
    public BasicData eval(Reader in, BasicContext ctx) {
        try{
            return eval(new ANTLRInputStream(in), ctx);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    private BasicData eval(ANTLRInputStream is, BasicContext ctx) {
        BasicLexer lexer = new BasicLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(cts);
        BasicParser.FileContext fileContext = parser.file();
        AbstractSyntaxTree ast = new AbstractSyntaxTree(fileContext);
        ast.execute(ctx);
        return ast.value(ctx);
    }
}
