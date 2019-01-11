package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;
import prj.clark.lang.basic.env.BasicData;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.util.ArrayList;
import java.util.List;

public class IdentifierNode extends ExpressionNode {
    private String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public BasicData evaluate(BasicContext ctx) {
        Bindings bindings = ctx.getBindings(ScriptContext.ENGINE_SCOPE);

        if (! bindings.containsKey(identifier)) {
            throw new IllegalStateException("No such identifier: " + identifier + " in scope.");
        }

        return (BasicData) bindings.get(identifier);
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }
}
