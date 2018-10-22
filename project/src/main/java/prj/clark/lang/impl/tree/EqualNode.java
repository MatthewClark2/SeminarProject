package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.LangBool;

public class EqualNode extends BinaryOperator {

    public EqualNode(Node left, Node right) {
        super(left, right, (l, r) -> LangBool.of(l.equals(r)));
    }
}
