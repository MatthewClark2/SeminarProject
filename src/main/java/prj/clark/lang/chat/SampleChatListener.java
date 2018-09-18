package prj.clark.lang.chat;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SampleChatListener extends ChatBaseListener {
    // We use this in order to write information out via the ChatContext.
    private OutputStream os;

    public SampleChatListener(OutputStream os) {
        this.os = os;
    }

    @Override
    public void enterName(ChatParser.NameContext ctx) {
        write(Manipulation.BOLD.getValue());
    }

    @Override
    public void exitName(ChatParser.NameContext ctx) {
        // Obtain the given word.
        write(ctx.WORD().getText() + " ");
        reset();
    }

    @Override
    public void enterLink(ChatParser.LinkContext ctx) {
        write(Manipulation.BOLD.getValue() + Manipulation.BLUE.getValue());
    }

    @Override
    public void exitLink(ChatParser.LinkContext ctx) {
        reset();
    }

    @Override
    public void enterColor(ChatParser.ColorContext ctx) {
        write(Manipulation.match(ctx.WORD().getText()).getValue());
    }

    @Override
    public void exitColor(ChatParser.ColorContext ctx) {
        reset();
    }

    @Override
    public void exitEmoticon(ChatParser.EmoticonContext ctx) {
        String emote = ctx.getText();
        switch (emote) {
            case ":)":
            case ":-)":
                write("\uD83D\uDE42");
                break;
            case ":(":
            case ":-(":
                write("\uD83D\uDE41");
                break;
        }
    }

    @Override
    public void exitMessage(ChatParser.MessageContext ctx) {
        write(ctx.getText() + " ");
    }

    @Override
    public void enterCommand(ChatParser.CommandContext ctx) {
        if (ctx.SAYS() != null) {
            write(ctx.SAYS().getText() + ": ");
        }

        if (ctx.SHOUTS() != null) {
            write(ctx.SHOUTS().getText().toUpperCase() + ": ");
        }
    }

    private void write(String msg) {
        try {
            os.write((msg).getBytes());
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public void exitLine(ChatParser.LineContext ctx) {
        write("\n");
    }

    @Override
    public void enterMention(ChatParser.MentionContext ctx) {
        write(Manipulation.BOLD.getValue() + Manipulation.GREEN.getValue());
    }

    @Override
    public void exitMention(ChatParser.MentionContext ctx) {
        write(ctx.getText() + " ");
        reset();
    }

    private void reset() {
        write(Manipulation.RESET);
    }
}
