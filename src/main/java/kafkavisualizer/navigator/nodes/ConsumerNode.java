package kafkavisualizer.navigator.nodes;

import kafkavisualizer.models.Consumer;

import javax.swing.tree.DefaultMutableTreeNode;

public final class ConsumerNode extends DefaultMutableTreeNode {
    public ConsumerNode(Consumer consumer) {
        super(consumer);
    }

    public Consumer getConsumer() {
        return (Consumer) userObject;
    }
}
