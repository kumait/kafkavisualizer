package kafkavisualizer.details.topic;


import kafkavisualizer.KafkaClient;
import kafkavisualizer.models.Cluster;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsOptions;
import org.apache.kafka.clients.admin.DeleteTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ClusterDetailsModel {
    private Cluster cluster;

    public synchronized List<TopicInfo> loadTopics() throws InterruptedException, ExecutionException {
        return KafkaClient.getTopics(cluster.getServers());
    }

    public synchronized void createTopic(String name, int partitions, short replicationFactor) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", cluster.getServers());
        try (var admin = Admin.create(props)) {
            var newTopic = new NewTopic(name, partitions, replicationFactor);
            var options = new CreateTopicsOptions();
            options.timeoutMs(2000);
            admin.createTopics(List.of(newTopic), options).values().get(name).get();
        }
    }

    public synchronized void deleteTopics(Collection<String> names) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", cluster.getServers());
        try (var admin = Admin.create(props)) {
            var options = new DeleteTopicsOptions();
            options.timeoutMs(10000);
            admin.deleteTopics(names, options).all().get();
        }
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
