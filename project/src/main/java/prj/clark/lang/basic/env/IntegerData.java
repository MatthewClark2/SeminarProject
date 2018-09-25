package prj.clark.lang.basic.env;

public class IntegerData implements BasicData {
    private long content;

    public IntegerData(String content) {
        try {
            this.content = Long.parseLong(content.trim());
        } catch (NumberFormatException e) {
            // TODO(matthew-c21) Throw a specific syntax error.
            throw new IllegalArgumentException("Expected integer. Found string.");
        }
    }

    public IntegerData(long content) {
        this.content = content;
    }

    @Override
    public DataType getType() {
        return DataType.INTEGER;
    }

    @Override
    public String getContent() {
        return String.valueOf(content);
    }

    @Override
    public String toString() {
        return getContent();
    }
}
