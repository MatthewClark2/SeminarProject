package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangBool;

public class NotEqualNode extends BinaryOperator {

    public NotEqualNode(Node left, Node right) {
        super(left, right, (l, r) -> LangBool.of(l.equals(r)));
    }
}
