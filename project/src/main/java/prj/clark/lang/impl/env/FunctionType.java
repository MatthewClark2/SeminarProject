package prj.clark.lang.impl.env;

import java.util.List;

public class FunctionType implements DataType {
    private List<DataType> args;

    public FunctionType(List<DataType> args) {
        this.args = args;
    }

    @Override
    public boolean ofType(DataType dt) {
        if (dt instanceof FunctionType) {
            FunctionType ft = (FunctionType) dt;
            return args.equals(ft.args);
        }

        return false;
    }
}
