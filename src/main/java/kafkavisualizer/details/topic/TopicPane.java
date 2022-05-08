package kafkavisualizer.details.topic;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class TopicPane extends JPanel {
    private final JTextField nameTextField;
    private final JTextField partitionsTextField;
    private final JTextField replicationFactorTextField;

    public TopicPane() {
        var layout = new MigLayout(
                "fillx, wrap2",
                "[::120][]",
                "[][]"
        );
        setLayout(layout);

        add(new JLabel("Name"));
        nameTextField = new JTextField(30);
        add(nameTextField);

        add(new JLabel("Partitions"));
        partitionsTextField = new JTextField();
        add(partitionsTextField);

        add(new JLabel("Replication Factor"));
        replicationFactorTextField = new JTextField();
        add(replicationFactorTextField);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getPartitionsTextField() {
        return partitionsTextField;
    }

    public JTextField getReplicationFactorTextField() {
        return replicationFactorTextField;
    }
}
