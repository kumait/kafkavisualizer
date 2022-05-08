package kafkavisualizer.navigator.nodes;

import kafkavisualizer.models.Producer;

import javax.swing.tree.DefaultMutableTreeNode;

public final class ProducerNode extends DefaultMutableTreeNode {
    public ProducerNode(Producer producer) {
        super(producer);
    }

    public Producer getProducer() {
        return (Producer) userObject;
    }
}
