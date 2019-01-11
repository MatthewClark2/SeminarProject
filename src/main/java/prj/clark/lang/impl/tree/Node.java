package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;
import prj.clark.lang.impl.err.LangException;

public interface Node {
    Data evaluate(Context ctx) throws LangException;
}
