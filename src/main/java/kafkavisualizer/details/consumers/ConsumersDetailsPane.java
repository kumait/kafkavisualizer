package kafkavisualizer.details.consumers;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ConsumersDetailsPane extends JPanel {
    private final JButton newConsumerButton;

    public ConsumersDetailsPane() {
        var layout = new MigLayout(
                "",
                "[center, grow]",
                "[center, grow]"
        );

        setLayout(layout);
        newConsumerButton = new JButton();
        add(newConsumerButton);
    }

    public JButton getNewConsumerButton() {
        return newConsumerButton;
    }
}
