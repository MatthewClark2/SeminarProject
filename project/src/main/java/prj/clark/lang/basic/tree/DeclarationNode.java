package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import javax.script.Bindings;
import javax.script.ScriptContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeclarationNode implements Node {
    private String identifier;
    private ExpressionNode expression;

    public DeclarationNode(String identifier, ExpressionNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void execute(BasicContext ctx) throws IOException {
        Bindings bindings = ctx.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put(identifier, expression);
    }

    @Override
    public List<Node> getChildren() {
        return new ArrayList<Node>() {{
            add(expression);
        }};
    }
}
