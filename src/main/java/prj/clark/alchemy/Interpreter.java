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
import java.nio.file.Paths;

/**
 * This is just a rough version of the actual interpreter. Currently, all files are just ran as scripts, meaning that no
 * main method is required.
 */
public class Interpreter {
    private static NodeFactory factory = new NodeFactory();

    public static Data run(Path filename) throws IOException {
        return run(
                Files.readAllLines(filename).stream()
                        .reduce((l, r) -> l + "\n" + r)
                        .orElseThrow(() -> new RuntimeException("I'm not sure what happened.")));
    }

    public static Data run(String content) {
        Context ctx = new DefaultContext();
        ANTLRInputStream is = new ANTLRInputStream(content);
        AlchemyLexer lexer = new AlchemyLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        AlchemyParser parser = new AlchemyParser(cts);
        AlchemyParser.FileContext fileContext = parser.file();
        AbstractSyntaxTree ast = new AbstractSyntaxTree(factory.getAll(fileContext));
        return ast.execute(ctx);
    }

    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            System.out.println("Result of " + arg + " is " + run(Paths.get(arg)));
        }
    }
}
