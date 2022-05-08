package kafkavisualizer.navigator;

import javax.swing.*;
import java.awt.*;

public class NavigatorPane extends JPanel {
    //private final JPanel topPanel;
    private final JTree tree;

    public NavigatorPane() {
        //topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        //topPanel.setBorder(new FlatBorder());
        //topPanel.add(new JLabel("Navigator"));
        tree = new JTree();
        tree.setShowsRootHandles(true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        //add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    public JTree getTree() {
        return tree;
    }


}
