package kafkavisualizer.details.cluster;

import com.formdev.flatlaf.ui.FlatBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ClusterDetailsPane extends JPanel {
    private final ClusterInfoPane clusterInfoPane;

    public ClusterDetailsPane() {
        var layout = new MigLayout(
                "wrap 1",
                "8[grow]0",
                "8[]0"
        );
        setLayout(layout);
        setBorder(new FlatBorder());

        clusterInfoPane = new ClusterInfoPane();
        add(clusterInfoPane, "growx");
    }

    public ClusterInfoPane getClusterInfoPane() {
        return clusterInfoPane;
    }
}
