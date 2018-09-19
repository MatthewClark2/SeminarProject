package prj.clark.lang.chat;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChatApp {
    public static void main(String[] args) throws IOException {
        String input = "Matt SAYS: @tdobbs this /red/should/ work :)\n";

        // The next four lines show the heart of using Antlr to parse information.
        ANTLRInputStream chars = new ANTLRInputStream(new ByteArrayInputStream(input.getBytes()));
        ChatLexer lexer = new ChatLexer(chars);
        CommonTokenStream ts = new CommonTokenStream(lexer);
        ChatParser parser = new ChatParser(ts);

        // This is true by default, but is not guaranteed to be so in the future.
        parser.setBuildParseTree(true);
        ChatParser.ChatContext ctx = parser.chat();
        System.out.println(ctx.toStringTree());

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        // Now we create the new listener.
        SampleChatListener listener = new SampleChatListener(os);
        ParseTreeWalker.DEFAULT.walk(listener, ctx);

        System.out.println(os.toString());
    }
}
