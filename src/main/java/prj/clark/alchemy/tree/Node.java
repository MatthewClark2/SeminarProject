package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.err.LangException;

public interface Node {
    Data evaluate(Context ctx) throws LangException;
}
