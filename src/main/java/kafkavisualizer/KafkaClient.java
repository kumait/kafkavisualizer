package kafkavisualizer;

import kafkavisualizer.details.topic.TopicInfo;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.ListTopicsOptions;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class KafkaClient {
    public static List<TopicInfo> getTopics(String servers) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        try (var admin = Admin.create(props)) {
            var metrics = admin.metrics();
            var listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.listInternal(true);
            listTopicsOptions.timeoutMs(2000);
            var names = admin.listTopics(listTopicsOptions).names().get();
            var topicsMap = admin.describeTopics(names).allTopicNames().get();
            var topics = new ArrayList<TopicInfo>();
            for (var k: topicsMap.keySet()) {
                topics.add(new TopicInfo(k, topicsMap.get(k).partitions().size()));
            }
            return topics.stream().sorted(Comparator.comparing(TopicInfo::getName)).collect(Collectors.toList());
        }
    }

    public static String getVersion(String servers) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        try (var admin = Admin.create(props)) {
            var version = admin.metrics().entrySet().stream()
                    .filter(e -> "app-info".equals(e.getKey().group()))
                    .filter(e -> "version".equals(e.getKey().name()))
                    .map(e -> e.getValue().metricValue())
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .findFirst().orElse(null);
            return version;
        }
    }

    public static void getClusterId(String servers, BiConsumer<String, Throwable> callback) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        try (var admin = Admin.create(props)) {
            var options = new DescribeClusterOptions();
            options.timeoutMs(5000);
            admin.describeCluster(options).clusterId().whenComplete(callback::accept);
        } catch (Exception ex) {
            callback.accept(null, ex);
        }
    }

    public static void getTopics(String servers, BiConsumer<List<TopicInfo>, Exception> callback) {
        new Thread(() -> {
            try {
                var topics = getTopics(servers);
                callback.accept(topics, null);
            } catch (Exception ex) {
                callback.accept(null, ex);
            }
        }).start();
    }
}
