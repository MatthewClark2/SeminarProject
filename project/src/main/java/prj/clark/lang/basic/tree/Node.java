package prj.clark.lang.basic.tree;

import prj.clark.lang.basic.env.BasicContext;

import java.util.List;

interface Node {
    void execute(BasicContext ctx);
    List<Node> getChildren();
}
