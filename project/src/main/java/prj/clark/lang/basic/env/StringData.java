package prj.clark.lang.basic.env;

public class StringData implements BasicData {
    private String content;

    public StringData(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public DataType getType() {
        return DataType.STRING;
    }
}
