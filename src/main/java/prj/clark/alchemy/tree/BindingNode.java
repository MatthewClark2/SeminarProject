package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Invokable;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

/**
 * This class serves as means of execution for binding a single identifier to a value. Tuples use a different node.
 */
public class BindingNode implements Node {
    public enum BindingType {
        FUNCTION, VALUE, MODULE
    }

    private final String identifier;
    private final Valued body;
    private final BindingType bindingType;

    public BindingNode(String identifier, Valued body, BindingType bindingType) {
        this.identifier = identifier;
        this.body = body;
        this.bindingType = bindingType;
    }

    @Override
    public void execute(Context ctx) {
        Data d = body.evaluate(ctx);

        switch (bindingType) {
            case VALUE:
                ctx.bind(identifier, d);
                break;
            case MODULE:
                throw new UnsupportedOperationException("Modules not implemented yet.");
            case FUNCTION:
                if (!(d instanceof Invokable)) {
                    throw new IllegalStateException("Expected value of Invokable type.");
                }

                ctx.bindFunction(identifier, (Invokable)d);
                break;
        }
    }
}