package kafkavisualizer.components;

import com.formdev.flatlaf.ui.FlatBorder;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    private JPanel topPanel;
    private JLabel titleLabel;

    public TitlePanel(String title, JPanel contentPanel) {


        topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        //topPanel.setBackground(Color.decode("#F7F7F7"));
        topPanel.setBorder(new FlatBorder());
        titleLabel = new JLabel(title);
        topPanel.add(titleLabel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
