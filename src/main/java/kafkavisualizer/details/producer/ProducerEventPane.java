package kafkavisualizer.details.producer;

import kafkavisualizer.Utils;

import javax.swing.*;
import java.awt.*;

public class ProducerEventPane extends JPanel {
    private final JPanel valuePane;
    private final JPanel keyPane;
    private final JPanel headersPane;
    private final JTextArea valueTextArea;
    private final JTextArea keyTextArea;
    private final JTable headersTable;
    private final JToolBar headersToolbar;
    private final JButton addHeaderButton;
    private final JButton removeHeaderButton;

    public ProducerEventPane() {
        valuePane = new JPanel();
        valuePane.setLayout(new BorderLayout());
        valueTextArea = new JTextArea();
        valueTextArea.setLineWrap(false);
        valueTextArea.setFont(Utils.getMonoFont());
        valuePane.add(new JScrollPane(valueTextArea), BorderLayout.CENTER);

        keyPane = new JPanel();
        keyPane.setLayout(new BorderLayout());
        keyTextArea = new JTextArea();
        keyTextArea.setLineWrap(false);
        keyTextArea.setFont(Utils.getMonoFont());
        keyPane.add(new JScrollPane(keyTextArea), BorderLayout.CENTER);

        headersPane = new JPanel();
        headersPane.setLayout(new BorderLayout());

        headersToolbar = new JToolBar();
        addHeaderButton = new JButton();
        addHeaderButton.setHideActionText(true);
        removeHeaderButton = new JButton();
        removeHeaderButton.setHideActionText(true);
        headersToolbar.add(addHeaderButton);
        headersToolbar.add(removeHeaderButton);

        headersTable = new JTable();
        headersTable.setShowGrid(true);
        headersPane.add(headersToolbar, BorderLayout.NORTH);
        headersPane.add(new JScrollPane(headersTable), BorderLayout.CENTER);

        var tabbedPane = new JTabbedPane();
        tabbedPane.add("Value", valuePane);
        tabbedPane.add("Key", keyPane);
        tabbedPane.add("Headers", headersPane);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }

    public JPanel getValuePane() {
        return valuePane;
    }

    public JPanel getKeyPane() {
        return keyPane;
    }

    public JPanel getHeadersPane() {
        return headersPane;
    }

    public JTextArea getValueTextArea() {
        return valueTextArea;
    }

    public JTextArea getKeyTextArea() {
        return keyTextArea;
    }

    public JTable getHeadersTable() {
        return headersTable;
    }

    public JButton getAddHeaderButton() {
        return addHeaderButton;
    }

    public JButton getRemoveHeaderButton() {
        return removeHeaderButton;
    }
}
