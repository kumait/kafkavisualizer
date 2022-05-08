package kafkavisualizer.details.topic;

import java.util.Objects;

public final class TopicInfo {
    private final String name;
    private final int partitionCount;

    public TopicInfo(String name, int partitionCount) {
        this.name = name;
        this.partitionCount = partitionCount;
    }

    public String getName() {
        return name;
    }

    public int getPartitionCount() {
        return partitionCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicInfo topicInfo = (TopicInfo) o;
        return name.equals(topicInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
