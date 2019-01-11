package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangBool;

public class NotEqualNode extends BinaryOperator {

    public NotEqualNode(Node left, Node right) {
        super(left, right, (l, r) -> LangBool.of(! l.equals(r)));
    }
}
