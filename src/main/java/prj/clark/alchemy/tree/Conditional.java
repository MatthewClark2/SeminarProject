package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;

public class Conditional extends ReferentiallyTransparentValuedNode {
    private Valued ifTrue;
    private Valued ifFalse;
    private Valued condition;

    public Conditional(Valued ifTrue, Valued ifFalse, Valued condition) {
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.condition = condition;
    }

    @Override
    public Data evaluate(Context ctx) {
        // For now, this only works with actual booleans. Later, you'll need to add a casting logic to the Data.
        Data check = condition.evaluate(ctx);

        return check.toBoolean() ? ifTrue.evaluate(ctx) : ifFalse.evaluate(ctx);
    }
}
