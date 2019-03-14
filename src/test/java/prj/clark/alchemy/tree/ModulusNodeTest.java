package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;

public class ModulusNodeTest extends NumericBinaryOperatorNodeTest {

    @Override
    protected Data apply(Valued a, Valued b) {
        return new ModulusNode(a, b).evaluate(null);
    }

    @Override
    protected double apply(double a, double b) {
        return a % b;
    }
}
