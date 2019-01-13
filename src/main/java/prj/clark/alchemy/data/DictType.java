package prj.clark.alchemy.data;

public class DictType implements DataType {
    private DataType key;
    private DataType value;

    public DictType(DataType key, DataType value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean ofType(DataType dt) {
        if (dt instanceof DictType) {
            DictType o = (DictType) dt;
            return key.ofType(o.key) && value.ofType(o.value);
        }

        return false;
    }
}
