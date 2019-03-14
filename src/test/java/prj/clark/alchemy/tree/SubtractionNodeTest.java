package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.*;

public class SubtractionNodeTest extends NumericBinaryOperatorNodeTest {
    @Override
    protected double apply(double a, double b) {
        return a - b;
    }

    protected Data apply(Valued a, Valued b) {
        return new SubtractionNode(a, b).evaluate(null);
    }
}