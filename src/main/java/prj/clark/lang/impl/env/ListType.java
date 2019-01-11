package prj.clark.lang.impl.env;

public class ListType implements DataType {
    private DataType type;

    public ListType(DataType type) {
        this.type = type;
    }

    @Override
    public boolean ofType(DataType dt) {
        if (dt instanceof ListType) {
            return type.ofType(dt);
        }

        return false;
    }
}
