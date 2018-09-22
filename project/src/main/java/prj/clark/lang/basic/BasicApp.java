package prj.clark.lang.basic;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class BasicApp {
    public static void main(String[] args) {
        ANTLRInputStream is = new ANTLRInputStream("print 5 + 5");
        BasicLexer lexer = new BasicLexer(is);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        BasicParser parser = new BasicParser(cts);
        parser.setBuildParseTree(true);
        BasicParser.FileContext ctx = parser.file();

    }
}
