package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

public interface Node {
    Data evaluate(Context ctx);
}
