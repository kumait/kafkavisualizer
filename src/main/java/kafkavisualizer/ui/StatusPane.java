package kafkavisualizer.ui;

import javax.swing.*;
import java.awt.*;

public class StatusPane extends JPanel {
    private final JLabel statusLabel;

    public StatusPane() {
        super(new FlowLayout(FlowLayout.LEADING, 8, 0), true);
        statusLabel = new JLabel();
        statusLabel.setText("Kafka Visualizer");
        initComponents();
    }

    private void initComponents() {
        add(statusLabel);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
