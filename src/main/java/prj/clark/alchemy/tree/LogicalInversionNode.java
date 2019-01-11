package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;

public class LogicalInversionNode implements Node {
    private static final Converter BOOL_CONVERTER;

    private Node node;

    static {
        BOOL_CONVERTER = new BooleanConverter();
    }

    public LogicalInversionNode(Node node) {
        this.node = node;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        // TODO(matthew-c21) - Find a good way to cache this value.
        boolean result = ((LangBool) BOOL_CONVERTER.convert(node.evaluate(ctx))).getValue();

        return LangBool.of(! result);
    }
}
