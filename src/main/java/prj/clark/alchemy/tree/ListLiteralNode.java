package prj.clark.alchemy.tree;

import prj.clark.alchemy.data.Data;
import prj.clark.alchemy.data.EagerAlchemyList;
import prj.clark.alchemy.env.Context;

import java.util.List;
import java.util.stream.Collectors;

public class ListLiteralNode extends ReferentiallyTransparentValuedNode {
    private final List<Valued> elements;

    public ListLiteralNode(List<Valued> elements) {
        this.elements = elements;
    }

    @Override
    public Data evaluate(Context ctx) {
        return new EagerAlchemyList(elements.stream().map(x -> x.evaluate(ctx)).collect(Collectors.toList()));
    }
}
