package prj.clark.alchemy;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.env.Data;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.tree.AbstractSyntaxTree;
import prj.clark.alchemy.tree.NodeFactory;

import java.util.Scanner;

public class REPL {
    // This is only done as the factory contains no internal state.
    private static final NodeFactory factory;

    static {
        factory = new NodeFactory();
    }

    private static Data parse(ANTLRInputStream is, Context ctx) throws LangException {
        AlchemyLexer lexer = new AlchemyLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        AlchemyParser parser = new AlchemyParser(cts);
        AlchemyParser.FileContext fileContext = parser.file();
        AbstractSyntaxTree ast = new AbstractSyntaxTree(factory.getAll(fileContext));
        return ast.execute(ctx);
    }

    public static void main(String[] args) {
        System.out.println("This REPL is lame. Enter an EOF in order to exit cleanly.");
        Scanner sc = new Scanner(System.in);
        Context ctx = new DefaultContext();

        while (true) {
            displayPrompt();

            // Since checking if more input is available is a blocking operation, we do it in the same breath that we
            // assign the next read line.
            String input = sc.hasNextLine() ? sc.nextLine().trim() : null;

            if (input == null) {
                break;
            } else if (! input.isEmpty()) {
                try {
                    ANTLRInputStream is = new ANTLRInputStream(input);
                    System.out.println("\t" + parse(is, ctx));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void displayPrompt() {
        System.out.print(" >>> ");
    }
}
