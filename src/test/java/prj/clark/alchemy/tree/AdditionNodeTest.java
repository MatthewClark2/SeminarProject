package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;

public class AdditionNodeTest extends NumericBinaryOperatorNodeTest {
    @Override
    protected double apply(double a, double b) {
        return a + b;
    }

    protected Data apply(Valued a, Valued b) {
        return new AdditionNode(a, b).evaluate(null);
    }
}