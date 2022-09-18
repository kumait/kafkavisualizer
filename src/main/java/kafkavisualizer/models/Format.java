package kafkavisualizer.models;

public enum Format {
    PLAIN_TEXT("Plain Text"),
    JSON("JSON"),
    XML("XML");

    private final String stringValue;

    Format(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
