package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.models.Cluster;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.Producer;
import kafkavisualizer.navigator.nodes.ClusterNode;
import kafkavisualizer.navigator.nodes.ConsumerNode;
import kafkavisualizer.navigator.nodes.ProducerNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class RemoveAction extends AbstractAction {

    public RemoveAction() {
        super("Remove", new FlatSVGIcon("icons/removex.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Utils.showConfirmation(App.contentPane(), "Are you sure?")) {
            return;
        }

        var controller = App.getAppController().getNavigatorController();
        var selection = controller.getNavigatorPane().getTree().getSelectionPath();
        if (selection != null) {
            var selectedNode = (DefaultMutableTreeNode) selection.getLastPathComponent();
            if (selectedNode instanceof ClusterNode) {
                var clusterNode = (ClusterNode) selectedNode;
                var cluster = (Cluster) clusterNode.getUserObject();
                App.getAppController().getDetailsController().stopConsumers(cluster);
                controller.getNavigatorModel().getClusters().remove(cluster);
                controller.getTreeModel().removeNodeFromParent(selectedNode);
                EventBus.broadcast(Event.NAVIGATOR_NODE_REMOVED, selectedNode);
            } else if (selectedNode instanceof ProducerNode) {
                var producerNode = (ProducerNode) selectedNode;
                var producer = (Producer) producerNode.getUserObject();
                var producersNode = (DefaultMutableTreeNode) selectedNode.getParent();
                var cluster = (Cluster) ((DefaultMutableTreeNode) producersNode.getParent()).getUserObject();
                cluster.getProducers().remove(producer);
                controller.getTreeModel().removeNodeFromParent(selectedNode);
                EventBus.broadcast(Event.NAVIGATOR_NODE_REMOVED, selectedNode);
            } else if (selectedNode instanceof ConsumerNode) {
                var consumerNode = (ConsumerNode) selectedNode;
                var consumer = (Consumer) consumerNode.getUserObject();
                var consumersNode = (DefaultMutableTreeNode) selectedNode.getParent();
                var cluster = (Cluster) ((DefaultMutableTreeNode) consumersNode.getParent()).getUserObject();
                cluster.getConsumers().remove(consumer);
                controller.getTreeModel().removeNodeFromParent(selectedNode);
                EventBus.broadcast(Event.NAVIGATOR_NODE_REMOVED, selectedNode);
            }

            try {
                controller.getNavigatorModel().save();
            } catch (IOException ex) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), ex.getMessage());
            }
        }
    }
}
