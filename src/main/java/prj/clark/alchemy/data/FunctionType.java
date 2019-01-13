package prj.clark.alchemy.data;

import java.util.List;

public class FunctionType implements DataType {
    private List<DataType> args;
    private DataType returnType;

    public FunctionType(List<DataType> args, DataType returnType) {
        this.args = args;
        this.returnType = returnType;
    }

    @Override
    public boolean ofType(DataType dt) {
        if (dt instanceof FunctionType) {
            FunctionType ft = (FunctionType) dt;
            return args.equals(ft.args) && returnType.equals(ft.returnType);
        }

        return false;
    }
}
