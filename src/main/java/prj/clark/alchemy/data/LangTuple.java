package prj.clark.alchemy.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LangTuple implements Data {
    private final List<Data> data;
    private final DataType type;

    public LangTuple(List<Data> data) {
        this.data = data;

        if (data.isEmpty()) {
            type = EmptyType.get();
        } else {
            type = new TupleType(data.stream().map(Data::getType).collect(Collectors.toList()));
        }
    }

    @Override
    public DataType getType() {
        return type;
    }

    public List<Data> getData() {
        return new ArrayList<>(data);
    }
}
