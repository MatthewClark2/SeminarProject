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
        System.out.print(ctx.TEXT().getText());

        return visitChildren(ctx);
    }
}
