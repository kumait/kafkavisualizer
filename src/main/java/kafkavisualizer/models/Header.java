package kafkavisualizer.models;

import java.util.Objects;

public class Header {

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String value;
    private String key;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Header other = (Header) obj;
        return Objects.equals(key, other.key) && Objects.equals(value, other.value);
    }

}
