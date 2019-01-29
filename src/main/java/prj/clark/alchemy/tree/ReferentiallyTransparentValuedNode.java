package prj.clark.alchemy.tree;

import prj.clark.alchemy.env.Context;

/**
 * This class serves as a small utility to {@link Valued} nodes that don't do anything upon execution. This is a small
 * detail that cuts down on adding an empty method to implementing classes.
 */
public abstract class ReferentiallyTransparentValuedNode implements Valued {

    @Override
    public void execute(Context ctx) {
        // Do nothing.
    }
}
