package kafkavisualizer.models;

public class HeaderRow {
    private String key;
    private String value;

    public HeaderRow(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public HeaderRow() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
