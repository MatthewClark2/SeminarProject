package prj.clark.alchemy.env;

public enum PrimitiveType implements DataType {
    STRING, INT, FLOAT, BOOL;

    @Override
    public boolean ofType(DataType dt) {
        // The enum definition of equals is more than sufficient in this case.
        return this.equals(dt);
    }
}
