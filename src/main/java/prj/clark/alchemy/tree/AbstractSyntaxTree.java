package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.Empty;
import prj.clark.alchemy.err.LangException;

import java.util.List;

public class AbstractSyntaxTree {
    // Contains all top-level statements directly. The AST object itself is effectively the root of the tree.
    private List<Node> nodes;

    // TODO(matthew-c21) - Add constructors that allow for an easier time building the tree without a ton of extra steps.
    public AbstractSyntaxTree(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Execute the entire AST in one fell swoop given a particular execution context. NOTE: This method returns null if
     * there are no statements to execute. This should not be relied upon, as it will change in the future to an
     * explicit EMPTY value. Any code written with the assumption that this method returns null should be considered
     * faulty.
     * @param ctx the execution context for the AST.
     * @return the value of the last evaluated statement in the AST.
     * @throws LangException should any node in the tree fail.
     */
    public Data execute(Context ctx) throws LangException {
        Data d = Empty.get();

        for (Node n : nodes) {
            d = n.evaluate(ctx);
        }

        return d;
    }
}
