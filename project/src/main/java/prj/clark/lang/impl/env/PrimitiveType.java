package prj.clark.lang.impl.env;

public enum PrimitiveType implements DataType {
    STRING, INT, FLOAT, BOOL;

    @Override
    public boolean ofType(DataType dt) {
        // The enum definition of equals is more than sufficient in this case.
        return this.equals((Object) dt);
    }
}
