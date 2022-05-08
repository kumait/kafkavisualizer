package kafkavisualizer.models;

import java.util.Objects;
import java.util.UUID;

public final class Producer {
    private final String id;
    private String name;
    private String topic;

    public Producer(String name, String topic) {
        Objects.requireNonNull(topic);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.topic = topic;
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

    @Override
    public String toString() {
        if (name != null && name.trim().length() > 0) {
            return name;
        }

        return topic;
    }
}
