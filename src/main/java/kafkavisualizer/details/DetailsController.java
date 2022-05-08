package kafkavisualizer.details;

import kafkavisualizer.App;
import kafkavisualizer.details.cluster.ClusterDetailsController;
import kafkavisualizer.details.clusters.ClustersDetailsController;
import kafkavisualizer.details.consumer.ConsumerDetailsController;
import kafkavisualizer.details.consumers.ConsumersDetailsController;
import kafkavisualizer.details.producer.ProducerDetailsController;
import kafkavisualizer.details.producers.ProducersDetailsController;
import kafkavisualizer.details.topic.TopicsDetailsController;
import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.events.EventObserver;
import kafkavisualizer.models.Cluster;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.Producer;
import kafkavisualizer.navigator.nodes.*;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DetailsController implements EventObserver {
    private final Set<String> producers;
    private final Map<String, ConsumerDetailsController> consumers;
    private final DetailsPane detailsPane;
    private final ClustersDetailsController clustersDetailsController;
    private final ProducersDetailsController producersDetailsController;
    private final ConsumersDetailsController consumersDetailsController;
    private final ClusterDetailsController clusterDetailsController;
    private final TopicsDetailsController topicsDetailsController;

    public DetailsController() {
        EventBus.register(this);
        producers = new HashSet<>();
        consumers = new HashMap<>();

        detailsPane = new DetailsPane();

        clustersDetailsController = new ClustersDetailsController();
        producersDetailsController = new ProducersDetailsController();
        consumersDetailsController = new ConsumersDetailsController();
        clusterDetailsController = new ClusterDetailsController();
        topicsDetailsController = new TopicsDetailsController();
        detailsPane.add(new EmptyPane(), "empty");

        detailsPane.add(clustersDetailsController.getPane(), "clusters");
        detailsPane.add(producersDetailsController.getPane(), "producers");
        detailsPane.add(consumersDetailsController.getPane(), "consumers");
        detailsPane.add(clusterDetailsController.getClusterDetailsPane(), "cluster");
        detailsPane.add(topicsDetailsController.getTopicsPane(), "topics");
        showPanel("empty");
    }

    private void showPanel(String name) {
        var cardLayout = (CardLayout) detailsPane.getLayout();
        cardLayout.show(detailsPane, name);
    }

    public DetailsPane getDetailsPane() {
        return detailsPane;
    }

    private void showProducer(Producer producer) {
        if (!producers.contains(producer.getId())) {
            var producerDetailsController = new ProducerDetailsController();
            producerDetailsController.setProducer(producer);
            detailsPane.add(producerDetailsController.getProducerDetailsPane(), producer.getId());
            producers.add(producer.getId());
        }
        showPanel(producer.getId());
    }

    private void showConsumer(Consumer consumer) {
        if (!consumers.containsKey(consumer.getId())) {
            var consumerDetailsController = new ConsumerDetailsController(App.getAppController().getNavigatorController().getSelectedCluster(), consumer);
            detailsPane.add(consumerDetailsController.getPane(), consumer.getId());
            consumers.put(consumer.getId(), consumerDetailsController);
        }
        showPanel(consumer.getId());
    }

    private void deleteConsumer(Consumer consumer) {
        if (consumers.containsKey(consumer.getId())) {
            var controller = consumers.get(consumer.getId());
            controller.stop();
            consumers.remove(consumer.getId());
        }
    }

    @Override
    public void consume(Event event, Object payload) {
        if (event == Event.NAVIGATOR_SELECTION_CHANGED) {
            if (payload instanceof ClustersNode) {
                showPanel("clusters");
            } else if (payload instanceof ClusterNode) {
                var cluster = (Cluster)((ClusterNode)payload).getUserObject();
                clusterDetailsController.setCluster(cluster);
                showPanel("cluster");
            } else if (payload instanceof TopicsNode) {
                var cluster = App.getAppController().getNavigatorController().getSelectedCluster();
                topicsDetailsController.setCluster(cluster);
                showPanel("topics");
            } else if (payload instanceof ProducersNode) {
                showPanel("producers");
            } else if (payload instanceof ProducerNode) {
                var producer = (Producer)((ProducerNode)payload).getUserObject();
                showProducer(producer);
            } else if (payload instanceof ConsumersNode) {
                showPanel("consumers");
            } else if (payload instanceof ConsumerNode) {
                var consumer = (Consumer)((ConsumerNode)payload).getUserObject();
                showConsumer(consumer);
            } else {
                showPanel("empty");
            }
        } else if (event == Event.NAVIGATOR_NODE_REMOVED) {
            if (payload instanceof ClusterNode) {

            } else if (payload instanceof TopicsNode) {

            } else if (payload instanceof ProducerNode) {
                var producer = (Producer)((ProducerNode)payload).getUserObject();
                producers.remove(producer.getId());
            } else if (payload instanceof ConsumerNode) {
                var consumer = (Consumer)((ConsumerNode)payload).getUserObject();
                deleteConsumer(consumer);
            } else {

            }
        } else if (event == Event.APP_CLOSING) {
            stopConsumers(null);
        }
    }

    public void stopConsumers(Cluster cluster) {
        for (var id: consumers.keySet()) {
            var controller = consumers.get(id);
            if (cluster == null || cluster == controller.getCluster()) {
                controller.stop();
            }
        }
    }

    public void clearConsumers(Cluster cluster) {
        for (var id: consumers.keySet()) {
            var controller = consumers.get(id);
            if (cluster == null || cluster == controller.getCluster()) {
                controller.clear();
            }
        }
    }

    public ClusterDetailsController getClusterDetailsController() {
        return clusterDetailsController;
    }

    public TopicsDetailsController getTopicsDetailsController() {
        return topicsDetailsController;
    }
}
