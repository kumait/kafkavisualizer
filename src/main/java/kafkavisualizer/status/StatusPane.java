package kafkavisualizer.status;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusPane extends JPanel {
    private final JLabel westLabel;
    private final JLabel eastLabel;

    public StatusPane() {
        westLabel = new JLabel();
        eastLabel = new JLabel();
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(2, 4, 2, 4));
        setMinimumSize(new Dimension(100, 20));
        add(westLabel, BorderLayout.CENTER);
        add(eastLabel, BorderLayout.EAST);
    }

    public JLabel getWestLabel() {
        return westLabel;
    }

    public JLabel getEastLabel() {
        return eastLabel;
    }
}
