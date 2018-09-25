package prj.clark.lang.basic;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.tree.AbstractSyntaxTree;

import javax.script.ScriptContext;
import java.io.*;

public class BasicApp {
    public static void main(String[] args) {
        ANTLRInputStream is = new ANTLRInputStream("var hello = \"hi\"\nprint hello\nvar goodbye = \"goodbye\"");
        BasicLexer lexer = new BasicLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(cts);
        parser.setBuildParseTree(true);
        BasicParser.FileContext ctx = parser.file();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Writer writer = new BufferedWriter(new OutputStreamWriter(out));

        BasicContext bCtx = new BasicContext();
        bCtx.setWriter(writer);

        AbstractSyntaxTree ast = new AbstractSyntaxTree(ctx);
        ast.execute(bCtx);
        bCtx.getBindings(ScriptContext.ENGINE_SCOPE).forEach((x, y) -> System.out.println(x + ": " + y));
    }
}
