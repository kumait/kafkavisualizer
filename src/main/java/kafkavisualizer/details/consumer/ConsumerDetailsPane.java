package kafkavisualizer.details.consumer;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ConsumerDetailsPane extends JPanel {
    private final JToolBar toolBar;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton filterButton;
    private final JButton editButton;
    private final JButton clearButton;
    private final JTextField searchTextField;
    private final JTable table;
    private final ConsumerEventPane consumerEventPane;

    public ConsumerDetailsPane() {
        var layout = new MigLayout(
                "",
                "0[grow][trailing]0",
                "0[][grow]0"
        );
        setLayout(layout);

        toolBar = new JToolBar();
        startButton = new JButton();
        startButton.setHideActionText(true);
        stopButton = new JButton();
        stopButton.setHideActionText(true);
        clearButton = new JButton();
        clearButton.setHideActionText(true);
        filterButton = new JButton();
        filterButton.setHideActionText(true);
        editButton = new JButton();
        editButton.setHideActionText(true);
        toolBar.add(startButton);
        toolBar.add(stopButton);
        toolBar.add(clearButton);
        //toolBar.addSeparator();
        //toolBar.add(filterButton);
        toolBar.addSeparator();
        toolBar.add(editButton);

        add(toolBar);

        searchTextField = new JTextField(20);
        add(searchTextField, "wrap");
        table = new JTable();
        var scrollPane = new JScrollPane(table);
        consumerEventPane = new ConsumerEventPane();

        var splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, consumerEventPane);
        splitPane.setDividerLocation(300);
        add(splitPane, "span, grow");
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JTable getTable() {
        return table;
    }

    public ConsumerEventPane getConsumerEventPane() {
        return consumerEventPane;
    }
}
