package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.AlchemyBoolean;

public class OrNode extends BinaryOperator {

    public OrNode(Valued left, Valued right) {
        super(left, right, (l, r) -> AlchemyBoolean.of(l.toBoolean() || r.toBoolean()));
    }
}
