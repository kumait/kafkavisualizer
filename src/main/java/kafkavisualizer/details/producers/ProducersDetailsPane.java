package kafkavisualizer.details.producers;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ProducersDetailsPane extends JPanel {
    private final JButton newProducerButton;

    public ProducersDetailsPane() {
        var layout = new MigLayout(
                "",
                "[center, grow]",
                "[center, grow]"
        );

        setLayout(layout);
        newProducerButton = new JButton();
        add(newProducerButton);
    }

    public JButton getNewProducerButton() {
        return newProducerButton;
    }
}
