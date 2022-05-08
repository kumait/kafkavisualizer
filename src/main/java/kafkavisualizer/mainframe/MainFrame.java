package kafkavisualizer.mainframe;

import kafkavisualizer.navigator.NavigatorPane;
import kafkavisualizer.ui.DetailsPane;
import kafkavisualizer.ui.StatusPane;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private NavigatorPane navigatorPane;
    private DetailsPane detailsPane;
    private StatusPane statusPane;

    public MainFrame() throws HeadlessException {
        super("Kafka Visualizer");
        navigatorPane = new NavigatorPane();
        detailsPane = new DetailsPane();
        statusPane = new StatusPane();

        initComponents();

//        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(new JTree()), detailsPane);
//        splitPane.setDividerLocation(150);
//        getContentPane().add(splitPane);

    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 600));
        getContentPane().setLayout(new BorderLayout());
        navigatorPane = new NavigatorPane();

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigatorPane, detailsPane);
        splitPane.setDividerLocation(150);

        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusPane, BorderLayout.SOUTH);
    }
}
