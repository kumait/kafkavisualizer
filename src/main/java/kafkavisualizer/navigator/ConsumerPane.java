package kafkavisualizer.navigator;

import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.Format;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ConsumerPane extends JPanel {
    private final JTextField nameTextField;
    private final JComboBox<String> topicComboBox;
    private final JComboBox<Consumer.StartFrom> startFromComboBox;
    private final JComboBox<Format> keyFormatComboBox;
    private final JComboBox<Format> valueFormatComboBox;

    public ConsumerPane() {
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

        add(new JLabel("Start From"));
        startFromComboBox = new JComboBox<>();
        add(startFromComboBox);

        add(new JLabel("Value Format"));
        valueFormatComboBox = new JComboBox<>();
        add(valueFormatComboBox);

        add(new JLabel("Key Format"));
        keyFormatComboBox = new JComboBox<>();
        add(keyFormatComboBox);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JComboBox<String> getTopicComboBox() {
        return topicComboBox;
    }

    public JComboBox<Consumer.StartFrom> getStartFromComboBox() {
        return startFromComboBox;
    }

    public JComboBox<Format> getKeyFormatComboBox() {
        return keyFormatComboBox;
    }

    public JComboBox<Format> getValueFormatComboBox() {
        return valueFormatComboBox;
    }
}
