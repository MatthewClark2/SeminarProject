package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.LangBool;

public class EqualNode extends BinaryOperator {

    public EqualNode(Node left, Node right) {
        super(left, right, (l, r) -> LangBool.of(l.equals(r)));
    }
}
