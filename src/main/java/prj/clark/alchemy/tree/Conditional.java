package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;

public class Conditional implements Node {
    private static final Converter BOOL_CONVERTER;
    private Node ifTrue;
    private Node ifFalse;
    private Node condition;

    static {
        BOOL_CONVERTER = new BooleanConverter();
    }

    public Conditional(Node ifTrue, Node ifFalse, Node condition) {
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.condition = condition;
    }

    @Override
    public Data evaluate(Context ctx) throws LangException {
        // For now, this only works with actual booleans. Later, you'll need to add a casting logic to the Data.
        Data check = condition.evaluate(ctx);

        return ((LangBool) BOOL_CONVERTER.convert(check)).getValue() ? ifTrue.evaluate(ctx) : ifFalse.evaluate(ctx);
    }
}
