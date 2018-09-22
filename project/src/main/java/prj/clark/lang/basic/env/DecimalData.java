package prj.clark.lang.basic.env;

public class DecimalData implements BasicData {
    private double content;

    public DecimalData(String content) {
        try{
            this.content = Double.parseDouble(content.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected double. Found String.");
        }
    }

    public DecimalData(double content) {
        this.content = content;
    }

    @Override
    public DataType getType() {
        return DataType.DECIMAL;
    }

    @Override
    public String getContent() {
        return String.valueOf(content);
    }
}
