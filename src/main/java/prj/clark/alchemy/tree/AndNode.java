package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class AndNode extends BinaryOperator {

    public AndNode(Valued left, Valued right) {
        super(left, right, (l, r) -> AlchemyBoolean.of(l.toBoolean() && r.toBoolean()));
    }
}
