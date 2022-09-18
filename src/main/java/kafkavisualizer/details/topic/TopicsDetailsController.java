package kafkavisualizer.details.topic;

import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.details.topic.actions.DeleteTopicAction;
import kafkavisualizer.details.topic.actions.NewTopicAction;
import kafkavisualizer.details.topic.actions.RefreshTopicsAction;
import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.events.EventObserver;
import kafkavisualizer.models.Cluster;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TopicsDetailsController implements EventObserver, ListSelectionListener {
    private final TopicsPane topicsPane;
    private final ClusterDetailsModel model;
    private final TopicsModel topicsModel;
    private final NewTopicAction newTopicAction;
    private final RefreshTopicsAction refreshTopicsAction;
    private final DeleteTopicAction deleteTopicAction;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        var selection = topicsPane.getTable().getSelectedRows();
        deleteTopicAction.setEnabled(selection.length > 0);
    }

    public static class TopicsModel extends AbstractTableModel {
        private static final String[] colNames = {"Topic", "Partitions"};
        private List<TopicInfo> topics;

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        @Override
        public int getRowCount() {
            if (topics == null) {
                return 0;
            }
            return topics.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return topics.get(rowIndex).getName();
            } else if (columnIndex == 1) {
                return topics.get(rowIndex).getPartitionCount();
            }
            return null;
        }

        public List<TopicInfo> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicInfo> topics) {
            this.topics = topics;
        }
    }

    public TopicsDetailsController() {
        EventBus.register(this);
        topicsModel = new TopicsModel();
        topicsPane = new TopicsPane();
        model = new ClusterDetailsModel();
        topicsPane.getTable().setModel(topicsModel);
        topicsPane.getTable().getSelectionModel().addListSelectionListener(this);
        topicsPane.getTable().getColumnModel().getColumn(1).setMaxWidth(120);

        newTopicAction = new NewTopicAction();
        refreshTopicsAction = new RefreshTopicsAction(this);
        deleteTopicAction = new DeleteTopicAction(this);
        deleteTopicAction.setEnabled(false);

        topicsPane.getRefreshTopicsButton().setAction(refreshTopicsAction);
        topicsPane.getNewTopicButton().setAction(newTopicAction);
        topicsPane.getDeleteTopicButton().setAction(deleteTopicAction);
    }

    public TopicsPane getTopicsPane() {
        return topicsPane;
    }

    public Cluster getCluster() {
        return model.getCluster();
    }

    public void setCluster(Cluster cluster) {
        this.model.setCluster(cluster);
        updateView();
    }

    public void createTopic(String name, int partitions, short replicationFactor) {
        new Thread(() -> {
            try {
                model.createTopic(name, partitions, replicationFactor);
                loadTopics();
            } catch (Exception ex) {
                Utils.showError(topicsPane.getTopLevelAncestor(), ex.getMessage());
            }
        }).start();
    }

    public void loadTopics() {
        new Thread(() -> {
            try {
                App.getAppController().getStatusController().setWestStatus("Loading topics...");
                deleteTopicAction.setEnabled(false);
                refreshTopicsAction.setEnabled(false);
                newTopicAction.setEnabled(false);
                topicsModel.setTopics(null);
                topicsModel.fireTableDataChanged();
                var topics = model.loadTopics();
                topicsModel.setTopics(topics);
                topicsModel.fireTableDataChanged();
                App.getAppController().getStatusController().setWestStatus("Topics loaded");
            } catch (Exception e) {
                Utils.showError(topicsPane.getTopLevelAncestor(), e.getMessage());
                App.getAppController().getStatusController().setWestStatus("");
            } finally {
                deleteTopicAction.setEnabled(false);
                refreshTopicsAction.setEnabled(true);
                newTopicAction.setEnabled(true);
            }
        }).start();
    }

    public void deleteSelectedTopics() {
        var table = topicsPane.getTable();
        var selection = table.getSelectedRows();
        if (selection.length == 0) {
            return;
        }
        var names = Arrays.stream(selection).mapToObj(i -> topicsModel.topics.get(i).getName()).collect(Collectors.toList());
        new Thread(() -> {
            try {
                deleteTopicAction.setEnabled(false);
                refreshTopicsAction.setEnabled(false);
                newTopicAction.setEnabled(false);
                model.deleteTopics(names);
                topicsModel.getTopics().removeAll(names.stream().map(name -> new TopicInfo(name, 0)).collect(Collectors.toList()));
                topicsModel.fireTableDataChanged();
            } catch (Exception e) {
                Utils.showError(topicsPane.getTopLevelAncestor(), e.getMessage());
            } finally {
                deleteTopicAction.setEnabled(topicsPane.getTable().getSelectedRows().length > 0);
                refreshTopicsAction.setEnabled(true);
                newTopicAction.setEnabled(true);
            }
        }).start();
    }

    public void updateView() {
        loadTopics();
    }


    @Override
    public void consume(Event event, Object payload) {
//        if (event == Event.NAVIGATOR_SELECTION_CHANGED && payload instanceof Cluster) {
//            this.setCluster((Cluster)payload);
//            updateView();
//        }
    }

    public ClusterDetailsModel getModel() {
        return model;
    }

    public TopicsModel getTopicsModel() {
        return topicsModel;
    }
}
