package kafkavisualizer.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Producer {
    private final String id;
    private String name;
    private String topic;
    private String value;
    private String key;
    private List<Header> headers;

    public Producer(String name, String topic) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.topic = topic;
        headers = new ArrayList<>();
    }
    
    public Producer() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

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

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

	@Override
    public String toString() {
        if (name != null && name.trim().length() > 0) {
            return name;
        }

        return topic;
    }
}
