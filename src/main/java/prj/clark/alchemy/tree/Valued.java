package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.data.Data;

/**
 * This interface is used when dealing with {@link Node}s that should return a value upon invocation. Note that in most
 * cases, calls to {@link Node#execute(Context)} don't actually need to do anything. Such implementations may consider
 * inheriting from {@link ReferentiallyTransparentValuedNode} instead.
 */
public interface Valued extends Node {
    Data evaluate(Context ctx);
}
