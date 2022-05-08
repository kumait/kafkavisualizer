package kafkavisualizer.navigator;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.details.topic.actions.NewTopicAction;
import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.events.EventObserver;
import kafkavisualizer.models.Cluster;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.Producer;
import kafkavisualizer.navigator.actions.*;
import kafkavisualizer.navigator.nodes.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.io.IOException;
import java.util.List;

public class NavigatorController implements TreeNodeIconSupplier, TreeSelectionListener, EventObserver {
    public static final Icon FOLDER_ICON = new FlatSVGIcon("icons/folder.svg");
    public static final Icon CLUSTER_ICON = new FlatSVGIcon("icons/server.svg");
    public static final Icon TOPICS_ICON = new FlatSVGIcon("icons/array.svg");
    public static final Icon PRODUCER_ICON = new FlatSVGIcon("icons/right.svg");
    public static final Icon CONSUMER_ICON = new FlatSVGIcon("icons/left.svg");

    private final DefaultTreeModel treeModel;
    private final NavigatorPane navigatorPane;
    private final NavigatorModel navigatorModel;
    private final NewClusterAction newClusterAction;
    private final EditClusterAction editClusterAction;
    private final RemoveAction removeAction;
    private final NewTopicAction newTopicAction;
    private final NewProducerAction newProducerAction;
    private final EditProducerAction editProducerAction;
    private final NewConsumerAction newConsumerAction;
    private final EditConsumerAction editConsumerAction;

    public NavigatorController() {
        EventBus.register(this);
        navigatorModel = new NavigatorModel();
        navigatorPane = new NavigatorPane();
        newClusterAction = new NewClusterAction();
        editClusterAction = new EditClusterAction();
        removeAction = new RemoveAction();
        newTopicAction = new NewTopicAction();
        newProducerAction = new NewProducerAction();
        editProducerAction = new EditProducerAction();
        newConsumerAction = new NewConsumerAction();
        editConsumerAction = new EditConsumerAction();

        var rootNode = new ClustersNode();
        treeModel = new DefaultTreeModel(rootNode);
        var tree = navigatorPane.getTree();
        tree.setModel(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.getSelectionModel().addTreeSelectionListener(this);
        tree.setCellRenderer(new NavigatorTreeCellRenderer(this));
    }

    public DefaultMutableTreeNode getSelectedNode() {
        var selection = navigatorPane.getTree().getSelectionPath();
        if (selection != null) {
            return (DefaultMutableTreeNode) selection.getLastPathComponent();
        }
        return null;
    }

    public Cluster getSelectedCluster() {
        TreeNode clusterNode = null;
        DefaultMutableTreeNode selectedNode;
        if ((selectedNode = getSelectedNode()) != null) {
            if (selectedNode instanceof ClustersNode) {
                return null;
            } else if (selectedNode instanceof ClusterNode) {
                clusterNode = selectedNode;
            } else if (selectedNode instanceof ProducersNode || selectedNode instanceof ConsumersNode || selectedNode instanceof TopicsNode) {
                clusterNode = selectedNode.getParent();
            } else if (selectedNode instanceof ProducerNode || selectedNode instanceof ConsumerNode) {
                clusterNode = selectedNode.getParent().getParent();
            }
        }

        if (clusterNode == null) {
            return null;
        }

        var cluster = (Cluster)((DefaultMutableTreeNode)clusterNode).getUserObject();
        return cluster;
    }

    private void updatePopupMenu() {
        getNavigatorPane().getTree().setComponentPopupMenu(null);
        var selection = navigatorPane.getTree().getSelectionPath();
        if (selection != null) {
            var popupMenu = new JPopupMenu();
            var selectedNode = (DefaultMutableTreeNode)selection.getLastPathComponent();

            if (selectedNode instanceof ClustersNode) {
                popupMenu.add(new JMenuItem(newClusterAction));
            } else if (selectedNode instanceof ProducersNode) {
                popupMenu.add(new JMenuItem(newProducerAction));
            } else if (selectedNode instanceof ConsumersNode) {
                popupMenu.add(new JMenuItem(newConsumerAction));
            } else if (selectedNode instanceof ClusterNode) {
                popupMenu.add(new JMenuItem(editClusterAction));
                popupMenu.addSeparator();
                popupMenu.add(new JMenuItem(removeAction));
            } else if (selectedNode instanceof TopicsNode) {
                popupMenu.add(new JMenuItem(newTopicAction));
            } else if (selectedNode instanceof ProducerNode) {
                popupMenu.add(new JMenuItem(editProducerAction));
                popupMenu.addSeparator();
                popupMenu.add(new JMenuItem(removeAction));
            } else if (selectedNode instanceof ConsumerNode) {
                popupMenu.add(new JMenuItem(editConsumerAction));
                popupMenu.addSeparator();
                popupMenu.add(new JMenuItem(removeAction));
            }
            getNavigatorPane().getTree().setComponentPopupMenu(popupMenu);
        }
    }

    public NavigatorPane getNavigatorPane() {
        return navigatorPane;
    }

    public NavigatorModel getNavigatorModel() {
        return navigatorModel;
    }

    public void addCluster(Cluster cluster) {
        try {
            navigatorModel.addCluster(cluster);
            navigatorModel.save();

            var rootNode = (DefaultMutableTreeNode)treeModel.getRoot();
            var clusterNode = new ClusterNode(cluster);
            treeModel.insertNodeInto(clusterNode, rootNode, rootNode.getChildCount());
            navigatorPane.getTree().getSelectionModel().clearSelection();
            navigatorPane.getTree().getSelectionModel().addSelectionPath(new TreePath(clusterNode.getPath()));
            navigatorPane.getTree().scrollPathToVisible(new TreePath(clusterNode.getPath()));
        } catch (IOException e) {
            Utils.showError(navigatorPane, e.getMessage());
        }
    }

    public void addProducer(String name, String topic) {
        var cluster = getSelectedCluster();
        if (cluster == null) {
            return;
        }

        try {
            var producer = new Producer(name, topic);
            cluster.getProducers().add(producer);
            navigatorModel.save();

            var producersNode = (ProducersNode)getSelectedNode();
            var producerNode = new ProducerNode(producer);
            treeModel.insertNodeInto(producerNode, producersNode, producersNode.getChildCount());
            navigatorPane.getTree().getSelectionModel().clearSelection();
            navigatorPane.getTree().getSelectionModel().addSelectionPath(new TreePath(producerNode.getPath()));
            navigatorPane.getTree().scrollPathToVisible(new TreePath(producerNode.getPath()));
        } catch (IOException e) {
            Utils.showError(navigatorPane, e.getMessage());
        }
    }

    public void addConsumer(String name, String topic, Consumer.StartFrom startFrom) {
        var cluster = getSelectedCluster();
        if (cluster == null) {
            return;
        }

        try {
            var consumer = new Consumer(name, List.of(topic), startFrom);
            cluster.getConsumers().add(consumer);
            navigatorModel.save();

            var consumersNode = (ConsumersNode)getSelectedNode();
            var consumerNode = new ConsumerNode(consumer);
            treeModel.insertNodeInto(consumerNode, consumersNode, consumersNode.getChildCount());
            navigatorPane.getTree().getSelectionModel().clearSelection();
            navigatorPane.getTree().getSelectionModel().addSelectionPath(new TreePath(consumerNode.getPath()));
            navigatorPane.getTree().scrollPathToVisible(new TreePath(consumerNode.getPath()));
        } catch (IOException e) {
            Utils.showError(navigatorPane.getTopLevelAncestor(), e.getMessage());
        }
    }

    private void loadClusters() {
        try {
            navigatorModel.load();
            var rootNode = new ClustersNode();
            for (var cluster: navigatorModel.getClusters()) {
                var clusterNode = new ClusterNode(cluster);
                rootNode.add(clusterNode);

                for (var producer: cluster.getProducers()) {
                    var producerNode = new ProducerNode(producer);
                    clusterNode.getProducersNode().add(producerNode);
                }

                for (var consumer: cluster.getConsumers()) {
                    var consumerNode = new ConsumerNode(consumer);
                    clusterNode.getConsumersNode().add(consumerNode);
                }
            }
            treeModel.setRoot(rootNode);
            App.getAppController().getStatusController().setWestStatus("Clusters loaded");
        } catch (Exception e) {
            Utils.showError(navigatorPane, e.getMessage());
        }
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        updatePopupMenu();
        var selection = navigatorPane.getTree().getSelectionPath();
        if (selection == null) {
            EventBus.broadcast(Event.NAVIGATOR_SELECTION_CHANGED);
        } else {
            var selectedNode = (DefaultMutableTreeNode)selection.getLastPathComponent();
            EventBus.broadcast(Event.NAVIGATOR_SELECTION_CHANGED, selectedNode);
        }
    }

    @Override
    public Icon getTreeNodeIcon(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Icon icon = null;
        if (value instanceof DefaultMutableTreeNode) {
            var node = (DefaultMutableTreeNode)value;
            if (node instanceof ClusterNode) {
                icon = CLUSTER_ICON;
            } else if (node instanceof TopicsNode) {
                icon = TOPICS_ICON;
            } else if (node instanceof ProducerNode) {
                icon = PRODUCER_ICON;
            } else if (node instanceof ConsumerNode) {
                icon = CONSUMER_ICON;
            } else {
                icon = FOLDER_ICON;
            }
        }
        return icon;
    }

    @Override
    public void consume(Event event, Object payload) {
        if (event == Event.APP_STARTED) {
            loadClusters();
        }
    }
}
