package kafkavisualizer.details;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DetailsPane extends JPanel {
    public DetailsPane() {
        setBorder(new EmptyBorder(0, 0, 0, 8));
        setLayout(new CardLayout());
    }
}
