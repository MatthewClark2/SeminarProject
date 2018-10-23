package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.BooleanConverter;
import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Converter;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;

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
        return BOOL_CONVERTER.convert(node.evaluate(ctx));
    }
}
