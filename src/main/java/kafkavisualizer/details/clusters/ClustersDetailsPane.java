package kafkavisualizer.details.clusters;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ClustersDetailsPane extends JPanel {
    private final JButton newClusterButton;

    public ClustersDetailsPane() {
        var layout = new MigLayout(
                "",
                "[center, grow]",
                "[center, grow]"
        );

        setLayout(layout);
        newClusterButton = new JButton();
        add(newClusterButton);
    }

    public JButton getNewClusterButton() {
        return newClusterButton;
    }
}
