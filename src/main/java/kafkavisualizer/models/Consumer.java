package kafkavisualizer.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Consumer {
    public enum StartFrom {
        BEGINNING("Beginning"),
        NOW("Now");

        private final String description;

        StartFrom(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private final String id;
    private String name;
    private List<String> topics;
    private StartFrom startFrom;

    public Consumer(String name, List<String> topics, StartFrom startFrom) {
        Objects.requireNonNull(topics);
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.topics = topics;
        this.startFrom = startFrom;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public StartFrom getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(StartFrom startFrom) {
        this.startFrom = startFrom;
    }

    @Override
    public String toString() {
        if (name != null && name.trim().length() > 0) {
            return name;
        }

        return String.join(",", topics);
    }
}
