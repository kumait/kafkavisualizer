package kafkavisualizer.details.producer;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ProducerDetailsPane extends JPanel {
    private final ProducerEventPane producerEventPane;
    private final JButton sendButton;
    private final JButton editButton;

    public ProducerDetailsPane() {
        producerEventPane = new ProducerEventPane();

        var toolbar = new JToolBar();
        sendButton = new JButton();
        toolbar.add(sendButton);

        editButton = new JButton();
        editButton.setHideActionText(true);
        toolbar.addSeparator();
        toolbar.add(editButton);

        var layout = new MigLayout(
                "wrap 1",
                "0[grow]0",
                "0[][grow]0"
        );
        setLayout(layout);

        add(toolbar);
        add(producerEventPane, "grow");
    }

    public ProducerEventPane getProducerEventPane() {
        return producerEventPane;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getEditButton() {
        return editButton;
    }
}
