package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class EqualNode extends BinaryOperator {

    public EqualNode(Node left, Node right) {
        super(left, right, (l, r) -> AlchemyBoolean.of(l.equals(r)));
    }
}
