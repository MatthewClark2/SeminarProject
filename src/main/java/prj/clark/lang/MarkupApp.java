package prj.clark.lang;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class MarkupApp {
    public static void main(String[] args) {
        ANTLRInputStream is = new ANTLRInputStream("I'd like to emphasize [b]this[\\b] and italicize [u]that[\\u]." +
                "For fun, let's quote [quote author=\"Duke Nukem\"]I came here to chew ass and kick bubble gum, and I'm " +
                "all out of ass");
        MarkupLexer lexer = new MarkupLexer(is);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        MarkupParser parser = new MarkupParser(ts);

        MarkupParser.FileContext ctx = parser.file();
        MarkupVisitor visitor = new MarkupVisitor();
        visitor.visit(ctx);
    }
}
