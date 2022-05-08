package kafkavisualizer.details.cluster;

import kafkavisualizer.components.InfoLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ClusterInfoPane extends JPanel {
    private final JLabel nameLabel;
    private final JLabel serversLabel;

    public ClusterInfoPane() {
        var layout = new MigLayout(
                "wrap 2, fillx",
                "0[::50][][]0",
                "0[][][]0"
        );
        setLayout(layout);

        var topPane = new JPanel();
        topPane.setLayout(new BorderLayout());
        var titleLabel = new JLabel("Cluster Info");
        titleLabel.setFont(topPane.getFont().deriveFont(Font.BOLD));
        topPane.add(titleLabel, BorderLayout.WEST);
        add(topPane, "span, growx");

        add(new InfoLabel("Name"));
        nameLabel = new JLabel();
        add(nameLabel);

        add(new InfoLabel("Servers"));
        serversLabel = new JLabel();
        add(serversLabel, "growx");
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getServersLabel() {
        return serversLabel;
    }
}
