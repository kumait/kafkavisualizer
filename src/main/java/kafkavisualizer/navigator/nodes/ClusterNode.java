package kafkavisualizer.navigator.nodes;

import kafkavisualizer.models.Cluster;

import javax.swing.tree.DefaultMutableTreeNode;

public final class ClusterNode extends DefaultMutableTreeNode {
    public ClusterNode(Cluster cluster) {
        super(cluster);
        add(new TopicsNode());
        add(new ProducersNode());
        add(new ConsumersNode());
    }

    public Cluster getCluster() {
        return (Cluster) userObject;
    }

    public TopicsNode getTopicsNode() {
        return (TopicsNode) getChildAt(0);
    }

    public ProducersNode getProducersNode() {
        return (ProducersNode) getChildAt(1);
    }

    public ConsumersNode getConsumersNode() {
        return (ConsumersNode) getChildAt(2);
    }
}
