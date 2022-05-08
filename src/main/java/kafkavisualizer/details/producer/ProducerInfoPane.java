package kafkavisualizer.details.producer;

import kafkavisualizer.components.InfoLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ProducerInfoPane extends JPanel {
    private final JLabel nameLabel;
    private final JLabel topicLabel;

    public ProducerInfoPane() {
        var layout = new MigLayout(
                "wrap 2, fillx",
                "0[::50][][]0",
                "0[][][]0"
        );
        setLayout(layout);

        var topPane = new JPanel();
        topPane.setLayout(new BorderLayout());
        var titleLabel = new JLabel("Producer Info");
        titleLabel.setFont(topPane.getFont().deriveFont(Font.BOLD));
        topPane.add(titleLabel, BorderLayout.WEST);
        add(topPane, "span, growx");

        add(new InfoLabel("Name"));
        nameLabel = new JLabel();
        add(nameLabel);

        add(new InfoLabel("Topic"));
        topicLabel = new JLabel();
        add(topicLabel, "growx");
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getTopicLabel() {
        return topicLabel;
    }
}
