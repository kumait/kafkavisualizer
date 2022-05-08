package kafkavisualizer.details.cluster;

import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.events.EventObserver;
import kafkavisualizer.models.Cluster;

public class ClusterDetailsController implements EventObserver {
    private Cluster cluster;
    private final ClusterDetailsPane clusterDetailsPane;

    public ClusterDetailsController() {
        EventBus.register(this);
        clusterDetailsPane = new ClusterDetailsPane();
    }

    public ClusterDetailsPane getClusterDetailsPane() {
        return clusterDetailsPane;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
        updateView();
    }

    public void updateView() {
        clusterDetailsPane.getClusterInfoPane().getNameLabel().setText(cluster.getName());
        clusterDetailsPane.getClusterInfoPane().getServersLabel().setText(cluster.getServers());
    }

    @Override
    public void consume(Event event, Object payload) {
//        if (event == Event.NAVIGATOR_SELECTION_CHANGED && payload instanceof Cluster) {
//            this.setCluster((Cluster)payload);
//        }
    }

}
