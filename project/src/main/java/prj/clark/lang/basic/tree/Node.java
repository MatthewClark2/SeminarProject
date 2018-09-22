package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import java.io.IOException;
import java.util.List;

interface Node {
    void execute(BasicContext ctx) throws IOException;
    List<Node> getChildren();
}
