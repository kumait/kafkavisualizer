package kafkavisualizer.details.clusters;

import kafkavisualizer.navigator.actions.NewClusterAction;

public class ClustersDetailsController {
    private final ClustersDetailsPane pane;

    public ClustersDetailsController() {
        pane = new ClustersDetailsPane();
        pane.getNewClusterButton().setAction(new NewClusterAction());
    }

    public ClustersDetailsPane getPane() {
        return pane;
    }
}
