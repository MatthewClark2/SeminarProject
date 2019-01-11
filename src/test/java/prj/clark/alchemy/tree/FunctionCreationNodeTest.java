package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.env.*;
import prj.clark.alchemy.err.LangException;

import java.util.Arrays;
import java.util.Collections;

public class FunctionCreationNodeTest {
    @Test
    public void generatedValueOfCorrectType() throws LangException {
        Node n = new FunctionCreationNode(new StatementListNode(Collections.emptyList()), Collections.emptyList());
        Assert.assertEquals(RawFunction.getInstance(), n.evaluate(new DummyContext()).getType());
    }

    @Test
    public void generatedEmptyFunctionWorksCorrectly() throws LangException {
        Node n = new FunctionCreationNode(new StatementListNode(Collections.emptyList()), Collections.emptyList());
        Assert.assertEquals(Empty.get(), ((Function) n.evaluate(new DummyContext())).apply(Collections.emptyList()));
    }

    @Test
    public void generatedBasicFunctionWorksCorrectly() throws LangException {
        StatementListNode body = new StatementListNode(Collections.singletonList(
                new AdditionNode(
                        new IdentifierNode("x"),
                        new AdditionNode(
                                new IdentifierNode("y"),
                                new IdentifierNode("z")
                        )
                )
        ));

        Node n = new FunctionCreationNode(body, Arrays.asList("x", "y", "z"));
        Assert.assertEquals(LangInt.of(6), ((Function) n.evaluate(new DefaultContext())).apply(Arrays.asList(LangInt.of(1), LangInt.of(2), LangInt.of(3))));
    }
}