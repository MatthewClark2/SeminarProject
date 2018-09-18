package prj.clark.lang;

public class MarkupVisitor extends MarkupParserBaseVisitor<String> {
    @Override
    public String visitFile(MarkupParser.FileContext ctx) {
        visitChildren(ctx);

        System.out.println();

        return null;
    }

    @Override
    public String visitContent(MarkupParser.ContentContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitElement(MarkupParser.ElementContext ctx) {
        if (ctx.parent instanceof MarkupParser.FileContext) {
            if (ctx.content() != null) {
                System.out.print(visitContent(ctx.content()));
            }

            if (ctx.tag() != null) {
                System.out.print(visitTag(ctx.tag()));
            }
        }

        return null;
    }

    @Override
    public String visitTag(MarkupParser.TagContext ctx) {
        StringBuilder text = new StringBuilder();
        String start, end;
        start = end = "";

        String ID = ctx.ID(0).getText();

        switch (ID) {
            case "u":
                start = end = "*";
                break;
            case "b":
                start = end = "**";
                break;
            case "quote":
                String author = ctx.attribute().STRING().getText();

                // The actual author string still contains quotes, so we strip them.
                author = author.substring(1, author.length() - 1);

                start = "\n>\n> ";
                end = "\n> - " + author + "\n";
        }

        text.append(start);

        for (MarkupParser.ElementContext node : ctx.element()) {
            if (node.tag() != null) {
                text.append(visitTag(node.tag()));
            }

            if (node.content() != null) {
                text.append(visitContent(node.content()));
            }
        }

        text.append(end);
        return text.toString();
    }
}
