package kafkavisualizer.details.topic;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class TopicsPane extends JPanel {
    private final JTable table;
    private final JButton newTopicButton;
    private final JButton refreshTopicsButton;
    private final JButton deleteTopicButton;

    public TopicsPane() {
        var layout = new MigLayout(
                "wrap 1",
                "0[grow]0",
                "0[][grow]0"
        );
        setLayout(layout);

        var toolbar = new JToolBar();

        newTopicButton = new JButton();
        newTopicButton.setHideActionText(true);
        toolbar.add(newTopicButton);

        deleteTopicButton = new JButton();
        deleteTopicButton.setHideActionText(true);
        toolbar.add(deleteTopicButton);

        toolbar.addSeparator();

        refreshTopicsButton = new JButton();
        refreshTopicsButton.setHideActionText(true);
        toolbar.add(refreshTopicsButton);

        add(toolbar);

        table = new JTable();
        add(new JScrollPane(table), "span, grow");
    }

    public JTable getTable() {
        return table;
    }

    public JButton getNewTopicButton() {
        return newTopicButton;
    }

    public JButton getRefreshTopicsButton() {
        return refreshTopicsButton;
    }

    public JButton getDeleteTopicButton() {
        return deleteTopicButton;
    }
}
