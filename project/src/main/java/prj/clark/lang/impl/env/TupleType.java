package prj.clark.lang.impl.env;

import java.util.List;

public class TupleType implements DataType {
    private List<DataType> args;

    public TupleType(List<DataType> args) {
        this.args = args;
    }

    @Override
    public boolean ofType(DataType dt) {
        if (dt instanceof TupleType) {
            TupleType tt = (TupleType) dt;
            return args.equals(tt.args);
        }

        return false;
    }
}
