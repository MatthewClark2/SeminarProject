package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.env.LangBool;

public class Conditional implements Node {
    private Node ifTrue;
    private Node ifFalse;
    private Node condition;

    public Conditional(Node ifTrue, Node ifFalse, Node condition) {
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.condition = condition;
    }

    @Override
    public Data evaluate(Context ctx) {
        // For now, this only works with actual booleans. Later, you'll need to add a casting logic to the Data.
        Data check = condition.evaluate(ctx);

        if (check instanceof LangBool) {
            return ((LangBool) check).getValue() ? ifTrue.evaluate(ctx) : ifFalse.evaluate(ctx);
        }

        // You'll also need a check that the types match once you have working types.
        throw new IllegalStateException("Condition must be boolean.");
    }
}
