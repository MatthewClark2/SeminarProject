package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class NotEqualNode extends BinaryOperator {

    public NotEqualNode(Valued left, Valued right) {
        super(left, right, (l, r) -> AlchemyBoolean.of(! l.equals(r)));
    }
}
