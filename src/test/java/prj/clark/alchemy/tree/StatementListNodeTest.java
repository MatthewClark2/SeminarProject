package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class StatementListNodeTest {
    private static class PrintNode implements Node {
        private OutputStream os;

        private PrintNode(OutputStream os) {
            this.os = os;
        }

        @Override
        public Data evaluate(Context ctx) throws LangException {
            try {
                os.write('.');
            } catch (IOException e) {
                fail();
            }

            return null;
        }
    }

    @Test
    public void emptyStatementListReturnsEmpty() throws LangException {
        StatementListNode sln = new StatementListNode(Collections.emptyList());
        Assert.assertEquals(Empty.get(), sln.evaluate(new DummyContext()));
    }

    @Test
    public void allStatementsExecuted() throws LangException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StatementListNode sln = new StatementListNode(Arrays.asList(new PrintNode(os), new PrintNode(os), new PrintNode(os)));
        sln.evaluate(new DummyContext());
        assertEquals("...", os.toString());
    }

    @Test
    public void finalValueReturned() throws LangException {
        StatementListNode sln = new StatementListNode(Arrays.asList(
                new LiteralNode(LangBool.of(true)),
                new LiteralNode(LangInt.of(7)),
                new LiteralNode(LangString.of("hello"))
        ));

        Assert.assertEquals(LangString.of("hello"), sln.evaluate(new DummyContext()));
    }
}