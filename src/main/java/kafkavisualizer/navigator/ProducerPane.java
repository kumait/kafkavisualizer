package kafkavisualizer.navigator;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ProducerPane extends JPanel {
    private final JTextField nameTextField;
    private final JComboBox<String> topicComboBox;

    public ProducerPane() {
        var layout = new MigLayout(
                "fillx, wrap2",
                "[::120][]",
                "[][]"
        );
        setLayout(layout);

        add(new JLabel("Topic"));
        topicComboBox = new JComboBox<>();
        add(topicComboBox);

        add(new JLabel("Name"));
        nameTextField = new JTextField(30);
        add(nameTextField);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JComboBox<String> getTopicComboBox() {
        return topicComboBox;
    }
}
