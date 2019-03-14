package prj.clark.alchemy;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.tree.AbstractSyntaxTree;
import prj.clark.alchemy.tree.NodeFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is just a rough version of the actual interpreter.
 */
public class Interpreter {
    private static NodeFactory factory = new NodeFactory();

    public Data run(Path filename) throws IOException {
        return run(Files.readAllLines(filename).stream().reduce((l, r) -> l + "\n" + r).orElseThrow(() -> new RuntimeException("I'm not sure what happened.")));
    }

    public Data run(String content) {
        Context ctx = new DefaultContext();
        ANTLRInputStream is = new ANTLRInputStream(content);
        AlchemyLexer lexer = new AlchemyLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        AlchemyParser parser = new AlchemyParser(cts);
        AlchemyParser.FileContext fileContext = parser.file();
        AbstractSyntaxTree ast = new AbstractSyntaxTree(factory.getAll(fileContext));
        return ast.execute(ctx);
    }
}
