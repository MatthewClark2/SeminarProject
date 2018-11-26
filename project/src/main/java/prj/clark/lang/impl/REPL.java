package prj.clark.lang.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;
import prj.clark.lang.impl.tree.AbstractSyntaxTree;

public class REPL {
    private static Data parse(ANTLRInputStream is, Context ctx) throws LangException {
        LangLexer lexer = new LangLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(cts);
        LangParser.FileContext fileContext = parser.file();
        AbstractSyntaxTree ast = new AbstractSyntaxTree(null);
        return ast.execute(ctx);
    }

    public static void main(String[] args) {

    }
}
