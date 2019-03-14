package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

/**
 * This class serves as means of execution for binding a single identifier to a value. Tuples use a different node.
 */
public class BindingNode implements Node {
    private final String identifier;
    private final Valued body;
    // TODO(matthew-c21) - Replace with an enum or int constant instead.
    private final boolean isMutableBinding;

    public BindingNode(String identifier, Valued body, boolean isMutableBinding) {
        this.identifier = identifier;
        this.body = body;
        this.isMutableBinding = isMutableBinding;
    }

    @Override
    public void execute(Context ctx) {
        // TODO(matthew-c21) - Update this once Context has different binding formats.
        Data d = body.evaluate(ctx);
        ctx.bind(identifier, d);
    }
}