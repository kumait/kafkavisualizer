package kafkavisualizer.details.consumer;

import kafkavisualizer.Utils;
import kafkavisualizer.details.consumer.actions.ClearAction;
import kafkavisualizer.details.consumer.actions.StartAction;
import kafkavisualizer.details.consumer.actions.StopAction;
import kafkavisualizer.models.Cluster;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.HeaderRow;
import kafkavisualizer.navigator.actions.EditConsumerAction;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConsumerDetailsController {

    public static class HeadersTableModel extends AbstractTableModel {
        private static final String[] COL_NAMES = {"Key", "Value"};
        private final List<HeaderRow> headerRows = new ArrayList<>();

        @Override
        public String getColumnName(int column) {
            return COL_NAMES[column];
        }

        @Override
        public int getRowCount() {
            return headerRows.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var row = headerRows.get(rowIndex);
            if (columnIndex == 0) {
                return row.getKey();
            } else if (columnIndex == 1) {
                return row.getValue();
            }
            return null;
        }

        public List<HeaderRow> getHeaders() {
            return headerRows;
        }
    }

    public class ConsumerTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            updateConsumerPane();
        }
    }

    private final ConsumerDetailsPane pane;
    private final StartAction startAction;
    private final StopAction stopAction;
    private final ClearAction clearAction;
    private final EditConsumerAction editConsumerAction;
    private final ConsumerTableModel consumerTableModel;
    private final Consumer consumer;
    private final Cluster cluster;
    private final ConsumerModel consumerModel;
    private final HeadersTableModel headersTableModel;
    private final ConsumerTableSelectionListener consumerTableSelectionListener;

    public ConsumerDetailsController(Cluster cluster, Consumer consumer) {
        this.cluster = cluster;
        this.consumer = consumer;
        consumerModel = new ConsumerModel(cluster, consumer);
        consumerTableSelectionListener = new ConsumerTableSelectionListener();
        pane = new ConsumerDetailsPane();

        startAction = new StartAction(this);
        stopAction = new StopAction(this);
        stopAction.setEnabled(false);
        clearAction = new ClearAction(this);
        editConsumerAction = new EditConsumerAction();
        pane.getStartButton().setAction(startAction);
        pane.getStopButton().setAction(stopAction);
        pane.getEditButton().setAction(editConsumerAction);
        pane.getClearButton().setAction(clearAction);

        pane.getSearchTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                consumerTableModel.setSearchText(pane.getSearchTextField().getText());
                consumerTableModel.fireTableDataChanged();
            }
        });

        consumerTableModel = new ConsumerTableModel();
        pane.getTable().setModel(consumerTableModel);
        pane.getTable().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pane.getTable().getSelectionModel().addListSelectionListener(consumerTableSelectionListener);

        var columnModel = pane.getTable().getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(180); // timestamp
        columnModel.getColumn(0).setMaxWidth(180);

        columnModel.getColumn(1).setPreferredWidth(150); // topic
        columnModel.getColumn(1).setMaxWidth(200);

        columnModel.getColumn(2).setPreferredWidth(100); // partition
        columnModel.getColumn(2).setMaxWidth(100);

        columnModel.getColumn(3).setPreferredWidth(100); // offset
        columnModel.getColumn(3).setMaxWidth(100);

        columnModel.getColumn(4).setPreferredWidth(200); // key

        columnModel.getColumn(5).setPreferredWidth(300); // value


        pane.getConsumerEventPane().getValueTextAreaWordWrapCheckBox().addActionListener(e -> {
            var selected = pane.getConsumerEventPane().getValueTextAreaWordWrapCheckBox().isSelected();
            pane.getConsumerEventPane().getValueTextArea().setLineWrap(selected);
            pane.getConsumerEventPane().getValueTextArea().setWrapStyleWord(selected);
        });

        pane.getConsumerEventPane().getValueTextAreaFormatJSONCheckBox().addActionListener(e -> updateConsumerPane());
        pane.getConsumerEventPane().getKeyTextAreaFormatJSONCheckBox().addActionListener(e -> updateConsumerPane());

        pane.getConsumerEventPane().getKeyTextAreaWordWrapCheckBox().addActionListener(e -> {
            var selected = pane.getConsumerEventPane().getKeyTextAreaWordWrapCheckBox().isSelected();
            pane.getConsumerEventPane().getKeyTextArea().setLineWrap(selected);
            pane.getConsumerEventPane().getKeyTextArea().setWrapStyleWord(selected);
        });

        headersTableModel = new HeadersTableModel();
        pane.getConsumerEventPane().getHeadersTable().setModel(headersTableModel);
    }

    public void updateConsumerPane() {
        if (pane.getTable().getSelectedRow() != -1) {
            var selectedRow = pane.getTable().getSelectedRow();
            var record = consumerTableModel.getSelectedRecord(selectedRow);

            var formatValueJSON = pane.getConsumerEventPane().getValueTextAreaFormatJSONCheckBox().isSelected();
            var value = formatValueJSON ? Utils.beautifyJSON(record.value()) : record.value();
            pane.getConsumerEventPane().getValueTextArea().setText(value);

            var formatKeyJSON = pane.getConsumerEventPane().getKeyTextAreaFormatJSONCheckBox().isSelected();
            var key = formatKeyJSON ? Utils.beautifyJSON(record.key()) : record.key();
            pane.getConsumerEventPane().getKeyTextArea().setText(key);

            headersTableModel.getHeaders().clear();
            for (var header: record.headers()) {
                var v = header.value() == null ? null : new String(header.value(), StandardCharsets.UTF_8);
                headersTableModel.getHeaders().add(new HeaderRow(header.key(), v));
            }
            headersTableModel.fireTableDataChanged();

        } else {
            pane.getConsumerEventPane().getValueTextArea().setText("");
            pane.getConsumerEventPane().getKeyTextArea().setText("");
            headersTableModel.getHeaders().clear();
        }
    }

    public void start() {
        new Thread(() -> {
            startAction.setEnabled(false);
            stopAction.setEnabled(true);
            consumerModel.start((records) -> {
                if (!records.isEmpty()) {
                    var selectedRow = pane.getTable().getSelectedRow();
                    var selectedRecord = -1;
                    if (selectedRow != -1) {
                        selectedRecord = consumerTableModel.getSelectedRecordIndex(selectedRow);
                    }
                    for (var record: records) {
                        consumerTableModel.addRecord(record);
                    }
                    consumerTableModel.fireTableDataChanged();
                    if (selectedRecord != -1) {
                        pane.getTable().getSelectionModel().setSelectionInterval(0, consumerTableModel.getRowIndex(selectedRecord));
                    }
                }
            });
        }).start();
    }

    public void stop() {
        new Thread(() -> {
            consumerModel.stop();
            startAction.setEnabled(true);
            stopAction.setEnabled(false);
        }).start();
    }

    public void clear() {
        consumerTableModel.clear();
        consumerTableModel.fireTableDataChanged();
    }

    public ConsumerDetailsPane getPane() {
        return pane;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public Consumer getConsumer() {
        return consumer;
    }
}
