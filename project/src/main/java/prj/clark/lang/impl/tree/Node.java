package prj.clark.lang.impl.tree;

import prj.clark.lang.impl.env.Context;
import prj.clark.lang.impl.env.Data;

public interface Node {
    Data evaluate(Context ctx);
}
