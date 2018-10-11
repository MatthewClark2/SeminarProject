package prj.clark.lang.impl.env;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class LangArray extends Collection {
    private final List<Data> data;
    private final DataType type;

    public LangArray(List<Data> data) {
        ensureHomogeneous(data);
        this.data = new ArrayList<>(data);
        type = new ListType(data.isEmpty()? Empty.get() : data.get(0).getType());
    }

    private static void ensureHomogeneous(List<Data> data) {
        if (data.isEmpty()) {
            return;
        }

        DataType type = data.get(0).getType();
        assert data.stream().map(Data::getType).allMatch(type::ofType);
    }

    @Override
    public Iterator<Data> iter() {
        return data.iterator();
    }

    @Override
    public DataType getType() {
        return type;
    }
}
