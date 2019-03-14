package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;

public class GreaterThanTest extends NumericComparatorNodeTest {

    @Override
    protected Data apply(Valued left, Valued right) {
        return new GreaterThanNode(left, right).evaluate(null);
    }

    @Override
    protected boolean apply(double left, double right) {
        return left > right;
    }
}
