package prj.clark.lang.impl.env;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tuple implements Data {
    private final List<Data> data;
    private final DataType type;

    public Tuple(List<Data> data) {
        this.data = data;

        if (data.isEmpty()) {
            type = Empty.get();
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
